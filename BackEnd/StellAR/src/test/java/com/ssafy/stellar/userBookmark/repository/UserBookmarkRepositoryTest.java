package com.ssafy.stellar.userBookmark.repository;

import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.star.repository.StarRepository;
import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.repository.UserRepository;
import com.ssafy.stellar.userBookmark.entity.UserBookmarkEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("User Star Bookmark Repository Unit-Test")
class UserBookmarkRepositoryTest {

    @Autowired
    private UserBookmarkRepository userBookmarkRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StarRepository starRepository;

    private UserEntity user;
    private StarEntity star1, star2;

    @BeforeEach
    void setup() {
        // Common user setup for all tests
        user = new UserEntity();
        user.setUserId("wncks");
        userRepository.save(user);

        // Common star setup for all tests
        star1 = new StarEntity();
        star1.setStarId("star1");
        starRepository.save(star1);

        star2 = new StarEntity();
        star2.setStarId("star2");
        starRepository.save(star2);
    }

    @Test
    @DisplayName("유저 북마크 리스트 조회")
    void findByUser() {
        String bookmarkName1 = "Bookmark Test1";
        String bookmarkName2 = "Bookmark Test2";


        // Given
        UserBookmarkEntity bookmark1 = new UserBookmarkEntity();
        bookmark1.setUser(user);
        bookmark1.setStar(star1);
        bookmark1.setBookmarkName(bookmarkName1);
        userBookmarkRepository.save(bookmark1);

        UserBookmarkEntity bookmark2 = new UserBookmarkEntity();
        bookmark2.setUser(user);
        bookmark2.setStar(star2);
        bookmark2.setBookmarkName(bookmarkName2);
        userBookmarkRepository.save(bookmark2);

        // When
        List<UserBookmarkEntity> result = userBookmarkRepository.findByUser(user);

        // Then
        assertThat(result).hasSize(2).extracting(UserBookmarkEntity::getBookmarkName)
                .containsExactlyInAnyOrder(bookmarkName1, bookmarkName2);

    }

    @Test
    @DisplayName("유저 북마크 개별 조회")
    void findByUserAndStar() {

        String bookmarkName = "bookmark Test";

        // Given
        UserBookmarkEntity bookmark = new UserBookmarkEntity();
        bookmark.setUser(user);
        bookmark.setStar(star1);
        bookmark.setBookmarkName(bookmarkName);
        userBookmarkRepository.save(bookmark);

        // When
        UserBookmarkEntity result = userBookmarkRepository.findByUserAndStar(user, star1);

        // Then
        assertThat(result).isNotNull()
                .extracting(UserBookmarkEntity::getBookmarkName)
                .isEqualTo(bookmarkName);;

    }

    @Test
    @DisplayName("유저 북마크 삭제")
    void deleteByUserAndStar() {

        String bookmarkName = "bookmark Test";

        // Given
        UserBookmarkEntity bookmark = new UserBookmarkEntity();
        bookmark.setUser(user);
        bookmark.setStar(star1);
        bookmark.setBookmarkName(bookmarkName);
        userBookmarkRepository.save(bookmark);

        // When
        userBookmarkRepository.deleteByUserAndStar(user, star1);

        // Then
        assertThat(userBookmarkRepository.findByUserAndStar(user, star1))
                .isNull();
    }
}