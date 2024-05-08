package com.ssafy.stellar.user.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserEntityTest {

    @Test
    @DisplayName("유저가 생성되는지 확인하는 테스트")
    void createUserTest(){
    /*
    given
     */
        UserEntity user = new UserEntity();
        user.setUserId("wnckswncks");
        user.setPassword("wncks1234");

    /*
    when, then
     */
        Assertions.assertThat(user.getUserId()).isEqualTo("wnckswncks");
    }

    @Test
    @DisplayName("유저의 이름이 변경되는지 확인하는 테스트")
    void setUserNameTest() {
        /*
        given
        */
        UserEntity user = new UserEntity();
        user.setUserId("wnckswncks");
        user.setPassword("wncks1234");

        /*
        when
         */
            user.setName("윤주찬짱");
        /*
        then
         */
        Assertions.assertThat(user.getName()).isEqualTo("윤주찬짱");
    }

}