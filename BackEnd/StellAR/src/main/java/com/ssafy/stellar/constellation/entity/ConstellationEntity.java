package com.ssafy.stellar.constellation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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

    @Temporal(TemporalType.DATE)
    @Column
    private Date constellationStartObservation;

    @Column
    private String constellationImg;

    @Column
    private String constellationStory;

    @Column
    private String constellationType;

    @Temporal(TemporalType.DATE)
    @Column
    private Date constellationEndObservation;


}
