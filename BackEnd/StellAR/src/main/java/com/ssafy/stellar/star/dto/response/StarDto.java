package com.ssafy.stellar.star.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StarDto {

    private String starId;
    private String starType;
    private double calX;
    private double calY;
    private double calZ;
    private String constellation;
    private String parallax;
    private String spType;
    private String hd;
    private String magV;

}
