package com.ssafy.stellar.constellation.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstellationLinkDto {

    private Integer linkId;
    private String starA;
    private String starB;
    private String constellationId;
}
