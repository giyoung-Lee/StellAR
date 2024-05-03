package com.ssafy.stellar.star.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanetDto {

    private String planetId;
    private String planetDEC;
    private String planetMagV;
    private String planetRA;
    private double calX;
    private double calY;
    private double calZ;
    private double nomalizedMagV;

}
