package com.ssafy.stellar.userBookmark.entity;


import static org.assertj.core.api.Assertions.assertThat;

import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.user.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

@DisplayName("User Star Bookmark Entity Unit-Test")
class UserBookmarkEntityTest {

    @Test
    @DisplayName("생성시간 테스트")
    void SetCreateTimeTest() {
        // Given
        UserBookmarkEntity bookmark = new UserBookmarkEntity();

        // When
        bookmark.onCreate();

        // Then
        assertThat(bookmark.getCreateTime()).isNotNull()
                .isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    @DisplayName("업데이트시간 테스트")
    void preUpdate_setCreateTime() {
        // Given
        UserBookmarkEntity bookmark = new UserBookmarkEntity();
        bookmark.setCreateTime(LocalDateTime.now().minusDays(1));

        // When
        bookmark.onUpdate();
        System.out.println("bookmark.getCreateTime() = " + bookmark.getCreateTime());

        // Then
        assertThat(bookmark.getCreateTime()).isNotNull()
                .isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    @DisplayName("북마크ID 설정 테스트")
    void setBookmarkId() {
        // Given
        UserBookmarkEntity bookmark = new UserBookmarkEntity();

        // When
        bookmark.setBookmarkId(1L);

        // Then
        assertThat(bookmark.getBookmarkId()).isNotNull();
    }

    @Test
    @DisplayName("유저 설정 테스트")
    void setUser() {
        // Given
        UserBookmarkEntity bookmark = new UserBookmarkEntity();

        // When
        UserEntity user = new UserEntity();
        user.setUserId("wncks");
        bookmark.setUser(user);

        // Then
        assertThat(bookmark.getUser()).isNotNull();
    }

    @Test
    @DisplayName("별 설정 테스트")
    void setStar() {
        // Given
        UserBookmarkEntity bookmark = new UserBookmarkEntity();

        // When
        StarEntity star = new StarEntity();
        star.setStarId("test star");
        bookmark.setStar(star);

        // Then
        assertThat(bookmark.getStar()).isNotNull();
    }

    @Test
    @DisplayName("별마크 이름 설정 테스트")
    void setBookmarkName() {
        // Given
        UserBookmarkEntity bookmark = new UserBookmarkEntity();

        // When
        bookmark.setBookmarkName("Test");

        // Then
        assertThat(bookmark.getBookmarkName()).isNotNull();
    }
}


