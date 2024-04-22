package com.ssafy.stellar.StructureTest.service;

import com.ssafy.stellar.StructureTest.entity.UserEntity;
import com.ssafy.stellar.StructureTest.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserEntity getUser(Long Id) {
        return userRepository.findById(Id);
    }
}
