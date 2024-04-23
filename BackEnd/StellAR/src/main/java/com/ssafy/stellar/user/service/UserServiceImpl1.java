package com.ssafy.stellar.user.service;

import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.repository.UserRepository1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserServiceImpl1 implements UserService1 {

    private final UserRepository1 userRepository;

    public UserServiceImpl1(UserRepository1 userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void signUp(UserEntity user) {

        userRepository.save(user);
    }

    @Override
    public UserEntity logIn(String userId, String password) {

        UserEntity user = userRepository.findByUserId(userId);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public boolean checkPassword(String userId, String password) {

        UserEntity user = userRepository.findByUserId(userId);

        if (user.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }
}
