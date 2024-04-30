package com.ssafy.stellar.constellation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "constellation")
public class ConstellationEntity {

    @Id
    @Column(name = "constellation_id")
    private String constellationId;

    @Column
    private String constellationSeason;

    @Column
    private String constellationDesc;

    @Column
    private String constellationSubName;

    @Column
    private String constellationStartObservation;

    @Column
    private String constellationImg;

    @Column
    private String constellationStory;

    @Column
    private String constellationType;

    @Column
    private String constellationEndObservation;


}
