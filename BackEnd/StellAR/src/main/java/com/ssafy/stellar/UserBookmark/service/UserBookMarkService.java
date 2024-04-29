package com.ssafy.stellar.UserBookmark.service;

import com.ssafy.stellar.UserBookmark.dto.request.BookmarkRequestDto;
import com.ssafy.stellar.UserBookmark.entity.UserBookmarkEntity;
import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.user.entity.UserEntity;

import java.util.List;

public interface UserBookMarkService {

    void createUserBookmark(BookmarkRequestDto bookmarkRequestDto);

    void updateUserBookmark(BookmarkRequestDto bookmarkRequestDto);

    List<UserBookmarkEntity> getUserBookmark(String userId);

    void deleteUserBookmark(String userId, String starId);
}
