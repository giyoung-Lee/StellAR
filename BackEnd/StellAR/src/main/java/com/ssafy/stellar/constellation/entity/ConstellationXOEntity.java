package com.ssafy.stellar.constellation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "constellation_question")
public class ConstellationXOEntity {


    @Id
    @Column(name = "constellation_question_id")
    private Integer constellationQuestionId;

    @Column(name = "constellation_question_contents")
    private String constellationQuestionContents;

    @Column(name = "constellation_question_answer")
    private String constellationQuestionAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "constellation_id")
    private ConstellationEntity constellationId;



}
