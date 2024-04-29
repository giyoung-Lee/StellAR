package com.ssafy.stellar.constellation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "constellation_link")
public class ConstellationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer linkId;

    @Column(name = "star_A")
    private String starA;

    @Column(name = "star_B")
    private String starB;

    @Column(name = "constellation_id")
    private String constellationId;
}
