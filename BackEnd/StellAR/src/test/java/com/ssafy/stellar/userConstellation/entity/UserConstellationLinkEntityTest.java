package com.ssafy.stellar.userConstellation.entity;

import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.user.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User Constellation Link Entity Unit-Test")
public class UserConstellationLinkEntityTest {

    @Test
    @DisplayName("링크 ID 설정")
    void setUserLinkIdTest() {
        // Given
        UserConstellationLinkEntity userConstellationLink = new UserConstellationLinkEntity();

        // When
        userConstellationLink.setUserLinkId(1L);

        // Then
        assertThat(userConstellationLink.getUserLinkId()).isNotNull()
                .isEqualTo(1L);

    }

    @Test
    @DisplayName("링크 유저 별자리 설정")
    void setUserConstellationTest() {
        // Given
        UserConstellationLinkEntity userConstellationLink = new UserConstellationLinkEntity();

        // When
        UserConstellationEntity userConstellation = new UserConstellationEntity();
        userConstellation.setUserConstellationId(1L);
        userConstellationLink.setUserConstellation(userConstellation);

        // Then
        assertThat(userConstellationLink.getUserConstellation()).isNotNull()
                .extracting(UserConstellationEntity::getUserConstellationId)
                .isEqualTo(1L);
    }

    @Test
    @DisplayName("링크 별A 설정")
    void setStarATest() {
        // Given
        UserConstellationLinkEntity userConstellationLink = new UserConstellationLinkEntity();

        // When
        StarEntity star = new StarEntity();
        star.setStarId("A");
        userConstellationLink.setStartStar(star);

        // Then
        assertThat(userConstellationLink.getStartStar()).isNotNull()
                .extracting(StarEntity::getStarId)
                .isEqualTo("A");
    }

    @Test
    @DisplayName("링크 별B 설정")
    void setStarBTest() {
        // Given
        UserConstellationLinkEntity userConstellationLink = new UserConstellationLinkEntity();

        // When
        StarEntity star = new StarEntity();
        star.setStarId("B");
        userConstellationLink.setEndStar(star);

        // Then
        assertThat(userConstellationLink.getEndStar()).isNotNull()
                .extracting(StarEntity::getStarId)
                .isEqualTo("B");

    }

    @Test
    @DisplayName("링크 전체 설정")
    void setUserLinkTest() {
        // Given & When
        UserConstellationEntity userConstellation = new UserConstellationEntity();
        userConstellation.setUserConstellationId(1L);
        StarEntity starA = new StarEntity();
        starA.setStarId("A");
        StarEntity starB = new StarEntity();
        starB.setStarId("B");
        UserConstellationLinkEntity userConstellationLink = new UserConstellationLinkEntity(1L, userConstellation, starA, starB);

        // Then
        assertThat(userConstellationLink.getUserLinkId()).isNotNull()
                .isEqualTo(1L);
        assertThat(userConstellationLink.getUserConstellation()).isNotNull()
                .extracting(UserConstellationEntity::getUserConstellationId)
                .isEqualTo(1L);
        assertThat(userConstellationLink.getStartStar()).isNotNull()
                .extracting(StarEntity::getStarId)
                .isEqualTo("A");
        assertThat(userConstellationLink.getEndStar()).isNotNull()
                .extracting(StarEntity::getStarId)
                .isEqualTo("B");

    }

}
