package com.ssafy.stellar.star.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StarInfoDto {

    private String starId;
    private String RA;
    private String Declination;
    private String PMRA;
    private String PMDEC;
    private String MagV;
    private String Parallax;
    private String SP_TYPE;
    private String Constellation;
    private String HD;
    private String starType;

}
