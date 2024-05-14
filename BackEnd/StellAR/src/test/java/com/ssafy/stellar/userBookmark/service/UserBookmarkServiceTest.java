package com.ssafy.stellar.userBookmark.service;

import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.star.repository.StarRepository;
import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.repository.UserRepository;
import com.ssafy.stellar.userBookmark.dto.request.BookmarkRequestDto;
import com.ssafy.stellar.userBookmark.dto.response.BookmarkDto;
import com.ssafy.stellar.userBookmark.entity.UserBookmarkEntity;
import com.ssafy.stellar.userBookmark.repository.UserBookmarkRepository;
import com.ssafy.stellar.utils.stars.CalcStarLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Star Bookmark Service Unit-Test")
public class UserBookmarkServiceTest {

    @InjectMocks
    private UserBookmarkServiceImpl userBookmarkService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StarRepository starRepository;

    @Mock
    private UserBookmarkRepository userBookmarkRepository;

    @Mock
    private CalcStarLocation calc;

    UserEntity user;
    StarEntity star1;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setUserId("wncks");
        lenient().when(userRepository.findByUserId("wncks")).thenReturn(user);

        star1 = new StarEntity();
        star1.setStarId("star1");
        star1.setRA("0 0 0");
        star1.setDeclination("+0 0 0");
        star1.setPMRA("0.0");
        star1.setPMDEC("0.0");
        lenient().when(starRepository.findByStarId("star1")).thenReturn(star1);
        lenient().when(calc.calculateNewRA(anyString(), anyDouble())).thenReturn(0.0);
        lenient().when(calc.calculateNewDec(anyString(), anyDouble())).thenReturn(0.0);
    }

    @Test
    @DisplayName("북마크 생성 성공 테스트")
    void manageUserBookmarkCreateSuccess() {
        // given
        String bookmarkName = "Bookmark Test";
        BookmarkRequestDto request = new BookmarkRequestDto(user.getUserId(), star1.getStarId(), bookmarkName);
        UserBookmarkEntity bookmark = new UserBookmarkEntity();
        bookmark.setUser(user);
        bookmark.setStar(star1);
        bookmark.setBookmarkName(bookmarkName);
        when(userBookmarkRepository.save(any(UserBookmarkEntity.class))).thenReturn(bookmark);

        // when
        userBookmarkService.manageUserBookmark(request, false);
        when(userBookmarkRepository.findByUserAndStar(user, star1)).thenReturn(bookmark);

        // then
        UserBookmarkEntity savedBookmark = userBookmarkRepository.findByUserAndStar(user, star1);
        assertThat(savedBookmark).isNotNull()
                .extracting(UserBookmarkEntity::getBookmarkName).isEqualTo(bookmarkName);
    }

    @Test
    @DisplayName("북마크 생성 실패 테스트")
    void manageUserBookmarkCreateFailure() {
        // given
        String bookmarkName = "Bookmark Test";
        BookmarkRequestDto request = new BookmarkRequestDto("wncks", "star1", bookmarkName);

        UserBookmarkEntity userBookmark = new UserBookmarkEntity();
        userBookmark.setUser(user);
        userBookmark.setStar(star1);
        userBookmark.setBookmarkName("Exsiting Bookmark");
        when(userBookmarkRepository.findByUserAndStar(user, star1)).thenReturn(userBookmark);

        // when & then
        assertThatThrownBy(() -> {
            userBookmarkService.manageUserBookmark(request, false);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Bookmark already exists for given user and star");

    }

    @Test
    @DisplayName("북마크 업데이트 성공 테스트")
    void manageUserBookmarkUpdateSuccess() {
        String bookmarkName = "Bookmark Update Test";
        BookmarkRequestDto request = new BookmarkRequestDto("wncks", "star1", bookmarkName);

        UserBookmarkEntity existingBookmark = new UserBookmarkEntity();
        existingBookmark.setUser(user);
        existingBookmark.setStar(star1);
        existingBookmark.setBookmarkName("Old Bookmark");
        when(userBookmarkRepository.findByUserAndStar(user, star1)).thenReturn(existingBookmark);

        // when
        userBookmarkService.manageUserBookmark(request, true);

        // then
        UserBookmarkEntity updatedBookmark = userBookmarkRepository.findByUserAndStar(user, star1);
        assertThat(updatedBookmark).isNotNull().extracting(UserBookmarkEntity::getBookmarkName).isEqualTo(bookmarkName);
    }

    @Test
    @DisplayName("북마크 업데이트 실패 테스트")
    void manageUserBookmarkUpdateFailure() {
        String bookmarkName = "Bookmark Update Test";
        BookmarkRequestDto request = new BookmarkRequestDto("wncks", "star1", bookmarkName);

        when(userBookmarkRepository.findByUserAndStar(user, star1)).thenReturn(null);

        // when
        assertThatThrownBy(() -> {
            userBookmarkService.manageUserBookmark(request, true);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Bookmark not found for given user and star");
    }

    @Test
    @DisplayName("북마크 조회 성공 테스트")
    void getUserBookmarkSuccess() {
        String bookmarkName = "Bookmark Update Test";
        UserBookmarkEntity existingBookmark = new UserBookmarkEntity();
        List<UserBookmarkEntity> bookmarkEntityList = new ArrayList<UserBookmarkEntity>();
        existingBookmark.setUser(user);
        existingBookmark.setStar(star1);
        existingBookmark.setBookmarkName(bookmarkName);
        bookmarkEntityList.add(existingBookmark);
        when(userBookmarkRepository.findByUser(user)).thenReturn(bookmarkEntityList);

        // when
        List<BookmarkDto> bookmarkListDto = userBookmarkService.getUserBookmark(user.getUserId());

        // then
        assertThat(bookmarkListDto).isNotNull().hasSize(1)
                .extracting(BookmarkDto::getBookmarkName)
                .containsOnly(bookmarkName);
    }

    @Test
    @DisplayName("북마크 조회 실패 테스트 (유저 아이디 없음)")
    void getUserBookmarkFailure() {
        when(userRepository.findByUserId(user.getUserId())).thenReturn(null);


        // then
        assertThatThrownBy(() -> {
            userBookmarkService.getUserBookmark(user.getUserId());
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User not found with id: " + user.getUserId());
    }

    @Test
    @DisplayName("북마크 조회 성공 테스트")
    void getUserBookmarkByStarSuccess() {
        String bookmarkName = "Bookmark Test";
        UserBookmarkEntity bookmark = new UserBookmarkEntity();
        bookmark.setUser(user);
        bookmark.setStar(star1);
        bookmark.setBookmarkName(bookmarkName);
        when(userBookmarkRepository.findByUserAndStar(user, star1)).thenReturn(bookmark);

        // when
        BookmarkDto bookmarkDto = userBookmarkService.getUserBookmarkByStar(user.getUserId(), star1.getStarId());

        // then
        assertThat(bookmarkDto).isNotNull()
                .extracting(BookmarkDto::getBookmarkName)
                .isEqualTo(bookmarkName);
    }

    @Test
    @DisplayName("북마크 조회 실패 테스트 - 별 없음")
    void getUserBookmarkByStarFailureOfStarId() {

        when(starRepository.findByStarId(star1.getStarId())).thenReturn(null);

        // then
        assertThatThrownBy(() -> {
            userBookmarkService.getUserBookmarkByStar(user.getUserId(), star1.getStarId());
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Star not found with id: " + star1.getStarId());
    }

    @Test
    @DisplayName("북마크 조회 실패 테스트 - 별마크 없음")
    void getUserBookmarkByStarFailureOfBookmark() {

        // given
        when(userBookmarkRepository.findByUserAndStar(user, star1)).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> {
            userBookmarkService.getUserBookmarkByStar(user.getUserId(), star1.getStarId());
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Bookmark not found for given user and star");
    }

    @Test
    @DisplayName("북마크 삭제 성공 테스트")
    void deleteUserBookmarkSuccess() {

        // given
        String bookmarkName = "Bookmark Test";
        UserBookmarkEntity bookmark = new UserBookmarkEntity();
        bookmark.setUser(user);
        bookmark.setStar(star1);
        bookmark.setBookmarkName(bookmarkName);
        when(userBookmarkRepository.findByUserAndStar(user, star1)).thenReturn(bookmark);

        // when
        userBookmarkService.deleteUserBookmark("wncks", "star1");

        // then
        verify(userBookmarkRepository).delete(bookmark);
    }

    @Test
    @DisplayName("북마크 삭제 실패 테스트 - 사용자 없음")
    void deleteUserBookmarkUserNotFound() {
        // given
        when(userRepository.findByUserId(user.getUserId())).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> userBookmarkService.deleteUserBookmark("wncks", "star1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User not found with id: " + user.getUserId());;
    }

    @Test
    @DisplayName("북마크 삭제 실패 테스트 - 별 없음")
    void deleteUserBookmarkStarNotFound() {
        // given
        when(starRepository.findByStarId(star1.getStarId())).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> userBookmarkService.deleteUserBookmark(user.getUserId(), star1.getStarId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Star not found with id: " + star1.getStarId());
    }

    @Test
    @DisplayName("북마크 삭제 실패 테스트 - 북마크 없음")
    void deleteUserBookmarkNotFound() {
        // given
        when(userBookmarkRepository.findByUserAndStar(user, star1)).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> {
            userBookmarkService.getUserBookmarkByStar(user.getUserId(), star1.getStarId());
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Bookmark not found for given user and star");
    }
}
