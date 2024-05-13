package com.ssafy.stellar.user.repository;

import com.ssafy.stellar.user.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.User;


@DataJpaTest
@DisplayName("User Repository Unit-Test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("유저 생성")
    void createUser() {

        // given
        UserEntity user1 = new UserEntity();
        user1.setUserId("wncks22");
        user1.setPassword("wncks1234");
        UserEntity user2 = new UserEntity();
        user2.setUserId("rldud22");
        user2.setPassword("rldud1234");
        user2.setName("기영이횽");
        user2.setGender("M");

        // when
        UserEntity result1 = userRepository.save(user1);
        UserEntity result2 = userRepository.save(user2);

        // then
        Assertions.assertThat(result1.getName()).isEqualTo(user1.getName());
        Assertions.assertThat(result2.getName()).isEqualTo(user2.getName());
    }
    @Test
    @DisplayName("유저 ID 검색 반환 하는지 확인")
    void findByUserId(){
        // given
        UserEntity user1 = new UserEntity();
        user1.setUserId("wncks22");
        user1.setPassword("wncks1234");
        UserEntity user2 = new UserEntity();
        user2.setUserId("rldud22");
        user2.setPassword("rldud1234");
        user2.setName("기영이횽");
        user2.setGender("M");

        // when
        userRepository.save(user1);
        userRepository.save(user2);

        UserEntity result1 = userRepository.findByUserId("wncks22");
        UserEntity result2 = userRepository.findByUserId("rldud22");

        // then
        Assertions.assertThat(result1.getUserId()).isEqualTo(user1.getUserId());
        Assertions.assertThat(result2.getUserId()).isEqualTo(user2.getUserId());
    }
}