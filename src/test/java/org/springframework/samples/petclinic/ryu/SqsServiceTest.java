package org.springframework.samples.petclinic.ryu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.PetClinicApplication;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PetClinicApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SqsServiceTest {

    @Autowired
    private SqsService sqsService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void sendMessageFIFO() throws JsonProcessingException {
        String uuid = UUID.randomUUID().toString();
        MessageVO messageVO = MessageVO.builder()
            .id(uuid)
            .text("hi~!")
            .delayTime(1000L)
            .build();
        String json = objectMapper.writeValueAsString(messageVO);
        log.info("request:{}", json);
        sqsService.sendMessageFIFO("yungwang.fifo", json);
    }

    @Test
    public void multiSendMessageFIFO() {
        IntStream.rangeClosed(1, 2000)
            .boxed()
            .map(i -> MessageVO.builder()
//                .id(UUID.randomUUID().toString())
                .id(String.valueOf(i))
                .text("hi~!")
                .delayTime(5000L)
                .build())
            .forEach(messageVO -> {
                try {
                    String json = objectMapper.writeValueAsString(messageVO);
                    sqsService.sendMessageFIFO("yungwang.fifo", json);
//                    Thread.sleep(500);
                } catch (JsonProcessingException e) {
                    log.warn(e.getMessage());
                }
            });
    }
}
