package com.ssafy.stellar.star.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "star")
public class StarEntity {

    @Id
    @Column(name = "star_id")
    private String starId;

    private String RA;

    private String Declination;

    private String PMRA;

    private String PMDEC;

    @Column(name = "Mag_V")
    private String MagV;

    private String Parallax;

    private String SP_TYPE;

    private String Constellation;

    private String HD;

    @Column(name = "Star_Type")
    private String starType;
}
