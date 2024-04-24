package com.ssafy.stellar.StructureTest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity(name = "test")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="gender")
    private String gender;

}
