package com.ssafy.stellar.userConstellation.entity;

import com.ssafy.stellar.user.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User Constellation Entity Unit-Test")
public class UserConstellationEntityTest {


    @Test
    @DisplayName("유저 별자리 생성 시간 테스트")
    void prePersist_setCreaeteTime() {
        // Given
        UserConstellationEntity userConstellation = new UserConstellationEntity();

        // When
        userConstellation.onCreate();

        // Then
        assertThat(userConstellation.getCreateDateTime()).isNotNull()
                .isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    @DisplayName("유저 별자리 ID 설정 테스트")
    void setUserConsteellationId() {
        // Given
        UserConstellationEntity userConstellation = new UserConstellationEntity();

        // When
        userConstellation.setUserConstellationId(1L);

        // Then
        assertThat(userConstellation.getUserConstellationId()).isNotNull()
                .isEqualTo(1L);
    }

    @Test
    @DisplayName("유저 별자리 유저 설정 테스트")
    void setUserTest() {
        // Given
        UserConstellationEntity userConstellation = new UserConstellationEntity();

        // When
        UserEntity user = new UserEntity();
        user.setUserId("wncks");
        userConstellation.setUser(user);

        // Then
        assertThat(userConstellation.getUser()).isNotNull()
                .extracting(UserEntity::getUserId)
                .isEqualTo("wncks");
    }

    @Test
    @DisplayName("유저 별자리 이름 설정 테스트")
    void setNameTest() {
        // Given
        UserConstellationEntity userConstellation = new UserConstellationEntity();

        // When
        userConstellation.setName("주찬자리");

        // Then
        assertThat(userConstellation.getName()).isNotNull()
                .isEqualTo("주찬자리");
    }

    @Test
    @DisplayName("유저 별자리 설명 설정 테스트")
    void setDescriptionTest() {
        // Given
        UserConstellationEntity userConstellation = new UserConstellationEntity();

        // When
        userConstellation.setDescription("주찬자리 입니다.");

        // Then
        assertThat(userConstellation.getDescription()).isNotNull()
                .isEqualTo("주찬자리 입니다.");
    }

    @Test
    @DisplayName("유저 별자리 전체 설정 테스트")
    void setUserConstellationTest() {
        // Given & When
        UserEntity user = new UserEntity();
        user.setUserId("wncks");
        UserConstellationEntity userConstellation = new UserConstellationEntity(1L, user, "주찬자리", "설명", LocalDateTime.now());

        // Then
        assertThat(userConstellation.getUserConstellationId()).isNotNull()
                .isEqualTo(1L);
        assertThat(userConstellation.getUser()).isNotNull()
                .extracting(UserEntity::getUserId)
                .isEqualTo("wncks");
        assertThat(userConstellation.getName()).isNotNull()
                .isEqualTo("주찬자리");
        assertThat(userConstellation.getDescription()).isNotNull()
                .isEqualTo("설명");
        assertThat(userConstellation.getCreateDateTime()).isNotNull()
                .isBeforeOrEqualTo(LocalDateTime.now());
    }
}
