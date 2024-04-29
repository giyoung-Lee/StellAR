package com.ssafy.stellar.UserBookmark.service;

import com.ssafy.stellar.UserBookmark.dto.request.BookmarkRequestDto;
import com.ssafy.stellar.UserBookmark.entity.UserBookmarkEntity;
import com.ssafy.stellar.UserBookmark.repository.UserBookmarkRepository;
import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.star.repository.StarRepository;
import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBookmarkServiceImpl implements UserBookMarkService {

    private final UserBookmarkRepository userBookmarkRepository;
    private final UserRepository userRepository;
    private final StarRepository starRepository;

    public UserBookmarkServiceImpl(
            UserBookmarkRepository userBookmarkRepository,
            UserRepository userRepository,
            StarRepository starRepository

    ) {
        this.userBookmarkRepository = userBookmarkRepository;
        this.userRepository = userRepository;
        this.starRepository = starRepository;
    }

    @Override
    public void createUserBookmark(BookmarkRequestDto bookmarkRequestDto) {
        UserEntity user = userRepository.findByUserId(bookmarkRequestDto.getUserId());
        StarEntity star = starRepository.findByStarId(bookmarkRequestDto.getStarId());
        UserBookmarkEntity bookmark = bookmarkRequestDto.toEntity(user, star);
        userBookmarkRepository.save(bookmark);

    }

    @Override
    public void updateUserBookmark(BookmarkRequestDto bookmarkRequestDto) {

    }

    @Override
    public List<UserBookmarkEntity> getUserBookmark(String userId) {
        return null;
    }

    @Override
    public void deleteUserBookmark(String userId, String starId) {

    }
}
