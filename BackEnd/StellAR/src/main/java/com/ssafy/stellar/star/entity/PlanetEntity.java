package com.ssafy.stellar.star.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "planet")
public class PlanetEntity {

    @Id
    @Column
    private String planetId;

    @Column(name = "planet_DEC")
    private String planetDEC;

    @Column(name = "planet_mag_v")
    private String planetMagV;

    @Column(name = "planet_RA")
    private String planetRA;
}
