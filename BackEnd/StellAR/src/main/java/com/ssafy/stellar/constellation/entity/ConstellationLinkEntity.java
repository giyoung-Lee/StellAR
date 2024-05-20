package com.ssafy.stellar.constellation.entity;

import com.ssafy.stellar.star.entity.StarEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "constellation_link")
public class ConstellationLinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer linkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "star_A")
    private StarEntity starA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "star_B")
    private StarEntity starB;

    @Column(name = "constellation_id")
    private String constellationId;

}
