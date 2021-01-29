package org.springframework.samples.petclinic.ryu;

import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SqsService {

    private final SimpleMessageListenerContainerFactory sqsFactory;

    public SendMessageResult sendMessageFIFO(String endPoint, String messageJSONData) {
        String messageId = DigestUtils.md5Hex(messageJSONData);
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
            .withQueueUrl(endPoint)
            .withMessageDeduplicationId(messageId)
            .withMessageGroupId(messageId + "-groupId")
            .withMessageBody(messageJSONData);
        return sqsFactory.getAmazonSqs().sendMessage(sendMessageRequest);
    }
}
