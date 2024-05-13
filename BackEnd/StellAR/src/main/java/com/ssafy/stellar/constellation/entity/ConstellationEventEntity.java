package com.ssafy.stellar.constellation.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "constellation_event")
public class ConstellationEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer constellationEventId;

    @Column
    private String astroEvent;

    @Column
    private String astroTime;

    @Column
    private LocalDate locdate;

}
