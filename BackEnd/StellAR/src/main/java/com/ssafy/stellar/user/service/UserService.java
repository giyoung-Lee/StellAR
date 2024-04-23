package com.ssafy.stellar.user.service;

import com.ssafy.stellar.user.entity.UserEntity;

public interface UserService {

    void signUp(String userId, String password, String name, String gender);


    UserEntity logIn(String userId, String password);


    boolean deleteUser(String userId);
}
