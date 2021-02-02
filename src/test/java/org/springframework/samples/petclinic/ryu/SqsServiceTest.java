package org.springframework.samples.petclinic.ryu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.PetClinicApplication;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

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
            .delayTime(10000L)
            .build();
        String json = objectMapper.writeValueAsString(messageVO);
        sqsService.sendMessageFIFO("yungwang.fifo", json);
    }
}
