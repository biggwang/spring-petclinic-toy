package org.springframework.samples.petclinic.owner

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.samples.petclinic.PetClinicApplication
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

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

        given:
        MockHttpServletRequestBuilder requestBuilder =
                request(HttpMethod.POST, '/owners/new')
                    .param("firstName", "류")
                    .param("lastName", "윤광")
                    .param("address", "하안로 364")
                    .param("city", "광명시")
                    .param("telephone", "01063292241")

        expect:
        mvc.perform(requestBuilder)
                .andReturn()
                .response
                .getStatus() == HttpStatus.OK.value()
    }

    @Unroll
    def "주소와 핸드폰 번호 파라미터 누락시 유효성 검사 실패 되는지 테스트 한다." () {

        given:
        MockHttpServletRequestBuilder requestBuilder =
                request(HttpMethod.POST, '/owners/new')
                        .param("firstName", "")
                        .param("lastName", "윤광")
                        .param("city", "광명시")

        expect:
        mvc.perform(requestBuilder)
                .andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().attributeHasFieldErrors("owner", "address"))
                .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andReturn()
                .response
    }
}
