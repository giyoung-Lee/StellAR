package com.ssafy.stellar.user.service;

import com.ssafy.stellar.user.dto.request.SignUpDto;
import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.repository.UserRepository1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserServiceImpl1 implements UserService1 {

    private final UserRepository1 userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl1(UserRepository1 userRepository,
                            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void signUp(SignUpDto signUpDto) {
        String encodePassword = bCryptPasswordEncoder.encode(signUpDto.getPassword());
        signUpDto.setPassword(encodePassword);
        UserEntity user = signUpDto.toEntity();
        userRepository.save(user);
    }

    @Override
    public UserEntity logIn(String userId, String password) {

        UserEntity user = userRepository.findByUserId(userId);
        if (user == null) {
            // 로그인 실패 처리 로직 또는 예외 발생
            throw new UsernameNotFoundException("User not found with id: " + userId);
        }

        if (checkPassword(userId, password)) {
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
        if (user == null) {
            // 로그인 실패 처리 로직 또는 예외 발생
            throw new UsernameNotFoundException("User not found with id: " + userId);
        }
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }

}
