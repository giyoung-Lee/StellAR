package com.ssafy.stellar.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity(name = "user_info")

public class UserEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 이거 주석해야하는데 이게 맞나??
    @Column(name="user_id")
    private String userId;

    @Column(name="user_password")
    private String password;

    @Column(name="user_name")
    private String name;

    @Column(name="user_gender")
    private String gender;
}
