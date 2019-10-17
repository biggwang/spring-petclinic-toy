package org.springframework.samples.petclinic.owner

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.samples.petclinic.PetClinicApplication
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request

@SpringBootTest(classes = PetClinicApplication.class)
@ContextConfiguration
@AutoConfigureMockMvc
//@WebMvcTest(controllers = [PetController])
class PetControllerTest extends Specification {


}
