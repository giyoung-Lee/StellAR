package com.ssafy.stellar.user.service;

import com.ssafy.stellar.user.dto.request.SignUpDto;
import com.ssafy.stellar.user.entity.UserEntity;

public interface UserService {

    void signUp(SignUpDto signUpDto);

    UserEntity logIn(String userId, String password);

    void deleteUser(String userId);

    boolean checkPassword(String userId, String password);
}
