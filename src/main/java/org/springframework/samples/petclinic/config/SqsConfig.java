package org.springframework.samples.petclinic.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@RequiredArgsConstructor
public class SqsConfig {

    @Value("${cloud.aws.region.static}")
    private String regionStatic;

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
        return new QueueMessagingTemplate(amazonSQSAsync);
    }

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder.standard()
            .withRegion(regionStatic)
            .withCredentials(new DefaultAWSCredentialsProviderChain())
            .withClientConfiguration(
                new ClientConfiguration()
                    .withMaxConnections(100)
                    .withConnectionTimeout(120 * 1000)
                    .withTcpKeepAlive(true)
            )
            .build();
    }

    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(AmazonSQSAsync amazonSQSAsync) {
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSQSAsync);
        factory.setMaxNumberOfMessages(10);
        factory.setAutoStartup(true);
        factory.setWaitTimeOut(20);
        factory.setTaskExecutor(threadPoolTaskExecutor());
        return factory;
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("sqs-queue-");
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(100);
        executor.setKeepAliveSeconds(60);   // 60s
        executor.initialize();
        return executor;
    }
}
