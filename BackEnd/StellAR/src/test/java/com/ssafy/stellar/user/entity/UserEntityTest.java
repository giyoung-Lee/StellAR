package com.ssafy.stellar.user.entity;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@DisplayName("User Entity Unit-Test")
class UserEntityTest {

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("유저가 생성되는지 확인하는 테스트")
    void createUserTest(){

        UserEntity user = new UserEntity();
        user.setUserId("wnckswncks");
        user.setPassword("wncks1234");

        assertThat(user.getUserId()).isEqualTo("wnckswncks");
    }

    @Test
    @DisplayName("비밀번호 변경 테스트")
    void changePassowrd(){
        String newPassword = "newPassword";
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        UserEntity user = new UserEntity();
        user.setUserId("wnckswncks");
        user.setPassword("wncks1234");

        user.setPassword(encodedPassword);

        assertThat(user.getPassword()).isEqualTo(encodedPassword);
    }

    @Test
    @DisplayName("유저 이름 설정 테스트")
    void setUserNameTest() {

        // given
        UserEntity user = new UserEntity();
        user.setUserId("wnckswncks");
        user.setPassword("wncks1234");

        // when
        user.setName("윤주찬짱");

        // then
        assertThat(user.getName()).isEqualTo("윤주찬짱");
    }

    @Test
    @DisplayName("유저 성별 설정 테스트")
    void setUserGenderTest() {

        // given
        UserEntity user = new UserEntity();
        user.setUserId("wnckswncks");
        user.setPassword("wncks1234");

        // when
        user.setGender("M");

        // then
        assertThat(user.getGender()).isEqualTo("M");
    }

}