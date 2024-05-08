package com.ssafy.stellar.user.service;

import com.ssafy.stellar.user.dto.request.SignUpDto;
import com.ssafy.stellar.user.dto.response.UserDto;

public interface UserService {

    void signUp(SignUpDto signUpDto);

    UserDto logIn(String userId, String password);

    void deleteUser(String userId, String password);

    boolean checkPassword(String userId, String password);
}
