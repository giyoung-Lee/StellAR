package com.ssafy.stellar.constellation.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ConstellationEventEntity {

    @Id
    @Column
    private Integer constellationEventId;

    @Column
    private String astroEvent;

    @Column
    private String astroTime;

    @Column
    private String astroTitle;

    @Column
    private String locdate;

}
