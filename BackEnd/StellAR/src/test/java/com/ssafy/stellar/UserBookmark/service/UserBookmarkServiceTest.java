package com.ssafy.stellar.UserBookmark.service;

import com.ssafy.stellar.UserBookmark.dto.request.BookmarkRequestDto;
import com.ssafy.stellar.UserBookmark.repository.UserBookmarkRepository;
import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.star.repository.StarRepository;
import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserBookmarkServiceTest {

    @Mock
    private UserBookmarkRepository userBookmarkRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StarRepository starRepository;

    @InjectMocks
    private UserBookmarkServiceImpl userBookmarkService;

    private UserEntity user;
    private StarEntity star;
    private BookmarkRequestDto requestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new UserEntity();
        star = new StarEntity();
        requestDto = new BookmarkRequestDto("wncks", "1 Bootes", "My Favorite Star");
    }

    @Test
    public void shouldFindUserByUserId() {
        // 준비
        when(userRepository.findByUserId("wncks")).thenReturn(new UserEntity());

        // 실행
        userBookmarkService.createUserBookmark(new BookmarkRequestDto("wncks", "1 Bootes", "My Favorite Star"));

        // 검증
        verify(userRepository).findByUserId("wncks");
    }

    @Test
    public void shouldFindStarByStarId() {
        // 준비
        when(userRepository.findByUserId(any())).thenReturn(new UserEntity());
        when(starRepository.findByStarId("1 Bootes")).thenReturn(new StarEntity());

        // 실행
        userBookmarkService.createUserBookmark(new BookmarkRequestDto("wncks", "1 Bootes", "My Favorite Star"));

        // 검증
        verify(starRepository).findByStarId("1 Bootes");
    }



    @Test
    void updateUserBookmark(UserEntity user, StarEntity star) {

    }

//    @Test
//    List<UserBookmarkEntity> getUserBookmark(String userId) {
//        return null;
//    }

    @Test
    void deleteUserBookmark(UserEntity user, StarEntity star) {

    }
}
