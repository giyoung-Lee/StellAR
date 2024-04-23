package com.ssafy.stellar.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity(name = "user_info")

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private String userId;

    @Column(name="user_password")
    private String password;

    @Column(name="user_name")
    private String name;

    @Column(name="user_gender")
    private String gender;
}
