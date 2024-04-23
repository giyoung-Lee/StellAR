package com.ssafy.stellar.user.service;

import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void signUp(String userId, String password, String name, String gender) {
        UserEntity user = new UserEntity();
        user.setUserId(userId);
        user.setPassword(password);
        user.setName(name);
        user.setGender(gender);

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
    public boolean deleteUser(String userId) {

        userRepository.deleteById(userId);
        return true;
    }
}
