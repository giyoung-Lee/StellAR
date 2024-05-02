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
    private double nomalizedMagV;
    private String RA;
    private String Declination;
    private String PMRA;
    private String PMDEC;

}
