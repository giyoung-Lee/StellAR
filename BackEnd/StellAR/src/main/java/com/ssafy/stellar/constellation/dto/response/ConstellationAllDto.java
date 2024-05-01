package com.ssafy.stellar.constellation.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConstellationAllDto {

    private String constellationId;
    private String constellationSeason;
    private String constellationDesc;
    private String constellationSubName;
    private String constellationStartObservation;
    private String constellationImg;
    private String constellationStory;
    private String constellationType;
    private String constellationEndObservation;

}
