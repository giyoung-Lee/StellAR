package com.ssafy.stellar.pieceOfKnowledge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "piece_knowledge")
public class PieceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer pieceKnowledge;

    @Column
    private String contents;

}
