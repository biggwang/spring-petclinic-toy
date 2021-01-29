package org.springframework.samples.petclinic.ryu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageVO {

    @Builder.Default
    private String id = "";
}
