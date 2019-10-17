package org.springframework.samples.petclinic.owner

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.samples.petclinic.PetClinicApplication
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.validation.FieldError
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request

@SpringBootTest(classes = PetClinicApplication.class)
@ContextConfiguration
@AutoConfigureMockMvc
class OwnerControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @Unroll
    def "주인 찾기 페이지 호출 테스트를 한다." () {

        expect:
        MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.GET, '/owners/find')

        mvc.perform(requestBuilder)
                .andReturn()
                .response
                .getStatus() == 200
    }

    @Unroll
    def "주인을 정상적으로 등록하는 테스트를 한다." () {

        expect:
        MockHttpServletRequestBuilder requestBuilder =
                request(HttpMethod.POST, '/owners/new')
                    .param("firstName", "류")
                    .param("lastName", "윤광")
                    .param("address", "하안로 364")
                    .param("city", "광명시")
                    .param("telephone", "01063292241")

        mvc.perform(requestBuilder)
                .andReturn()
                .response
                .getStatus() == 200
    }

    @Unroll
    def "주인을 등록하는 파라미터 유효성 실패 났을 경우 해당 메시지를 출력하는 테스트를 한다." () {

        when:
        MockHttpServletRequestBuilder requestBuilder =
                request(HttpMethod.POST, '/owners/new')
                        .param("firstName", "")
                        .param("lastName", "윤광")
                        .param("address", "하안로 364")
                        .param("city", "광명시")
                        .param("telephone", "01063292241")

        MockHttpServletResponse response =
                mvc.perform(requestBuilder)
                .andReturn()
                .response

        then:
        println(response.getErrorMessage())
        response.getErrorMessage() != ""
        response.get

    }
}
