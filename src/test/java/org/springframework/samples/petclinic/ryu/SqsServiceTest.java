package org.springframework.samples.petclinic.ryu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.PetClinicApplication;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PetClinicApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SqsServiceTest {

    @Autowired
    private SqsService sqsService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void sendMessageFIFO() throws JsonProcessingException {
        MessageVO messageVO = MessageVO.builder().id("gogo2").build();
        String json = objectMapper.writeValueAsString(messageVO);
        sqsService.sendMessageFIFO("yungwang.fifo", json);
    }
}
