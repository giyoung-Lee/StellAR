package com.ssafy.stellar.fcm.entity;

import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.userBookmark.entity.UserBookmarkEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DeviceToken Entity Unit-Test")
public class DeviceTokenEntityTest {

    @Test
    @DisplayName("디바이스 토큰 설정 테스트")
    void setDeviceTokenTest() {
        // Given
        DeviceTokenEntity deviceToken = new DeviceTokenEntity();

        // When
        deviceToken.setDeviceToken("device-token");

        // Then
        assertThat(deviceToken.getDeviceToken()).isNotNull()
                .isEqualTo("device-token");
    }

    @Test
    @DisplayName("유저 설정 테스트")
    void setUserTest() {
        // Given
        UserEntity user = new UserEntity("wncks", "password", "김싸피", "M");
        DeviceTokenEntity deviceToken = new DeviceTokenEntity();

        // When
        deviceToken.setUser(user);

        // Then
        assertThat(deviceToken.getUser()).isNotNull()
                .extracting(UserEntity::getUserId)
                .isEqualTo("wncks");
    }

    @Test
    @DisplayName("마지막 로그인 설정 테스트")
    void setLastLoginTest() {
        // Given
        DeviceTokenEntity deviceToken = new DeviceTokenEntity();

        // When
        deviceToken.setLastLogin(LocalDateTime.now());

        // Then
        assertThat(deviceToken.getLastLogin()).isNotNull()
                .isBeforeOrEqualTo(LocalDateTime.now());
    }
    @Test
    @DisplayName("디바이스 토큰 전체 설정 테스트")
    void createDeviceTokenTest() {
        // Given & When
        UserEntity user = new UserEntity("wncks", "password", "김싸피", "M");
        DeviceTokenEntity deviceToken = new DeviceTokenEntity("device-token", user, LocalDateTime.now());

        // When
        deviceToken.setLastLogin(LocalDateTime.now());

        // Then
        assertThat(deviceToken.getDeviceToken()).isNotNull()
                .isEqualTo("device-token");
        assertThat(deviceToken.getUser()).isNotNull()
                .extracting(UserEntity::getUserId)
                .isEqualTo("wncks");
        assertThat(deviceToken.getLastLogin()).isNotNull()
                .isBeforeOrEqualTo(LocalDateTime.now());
    }
}
