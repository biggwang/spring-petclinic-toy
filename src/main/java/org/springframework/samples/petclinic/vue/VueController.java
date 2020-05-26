package org.springframework.samples.petclinic.vue;

import java.util.Map;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class VueController {

    @GetMapping("/hello-vue")
    public String helloVue (Map<String, Object> model) {
        Owner owner = new Owner();
        model.put("owner", owner);
        return "vue/helloVue";
    }

}


