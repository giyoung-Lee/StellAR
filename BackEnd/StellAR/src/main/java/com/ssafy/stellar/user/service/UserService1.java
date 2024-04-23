package com.ssafy.stellar.user.service;

import com.ssafy.stellar.user.entity.UserEntity;

public interface UserService1 {

    void signUp(UserEntity user);

    UserEntity logIn(String userId, String password);

    void deleteUser(String userId);

    boolean checkPassword(String userId, String password);
}
