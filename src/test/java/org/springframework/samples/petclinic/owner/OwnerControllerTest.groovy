package org.springframework.samples.petclinic.owner

import org.assertj.core.util.Lists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.samples.petclinic.PetClinicApplication
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import spock.lang.Specification
import spock.lang.Unroll

import static org.mockito.BDDMockito.given
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@SpringBootTest(classes = PetClinicApplication.class)
@ContextConfiguration
@AutoConfigureMockMvc
class OwnerControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @MockBean
    private OwnerRepository owners

    private Owner owner;

    def setup() {
        owner = new Owner();
        owner.setId(1);
        owner.setFirstName("류");
        owner.setLastName("윤광");
        owner.setAddress("윌스트리트 323");
        owner.setCity("광명");
        owner.setTelephone("6085551023");
    }

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

    @Unroll
    def "주인 마지막 이름이 존재하지 않을 경우 리스트 화면을 출력한다." () {

        given:
        given(this.owners.findByLastName("")).willReturn(Lists.newArrayList(owner, new Owner()));

        expect:
        mvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"));
    }

    @Unroll
    def "주인 마지막 이름이 검색 되었을 경우 상세 페이지로 라다이렉트 된다." () {

        given:
        given(this.owners.findByLastName("윤광")).willReturn(Lists.newArrayList(owner));

        expect:
        mvc.perform(get("/owners")
            .param("lastName", "윤광")
        )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owners/" + 1))
    }
}
