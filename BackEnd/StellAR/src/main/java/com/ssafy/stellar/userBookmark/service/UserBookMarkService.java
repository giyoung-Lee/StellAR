package com.ssafy.stellar.userBookmark.service;

import com.ssafy.stellar.userBookmark.dto.request.BookmarkRequestDto;
import com.ssafy.stellar.userBookmark.dto.response.BookmarkDto;
import com.ssafy.stellar.userBookmark.entity.UserBookmarkEntity;
import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.user.entity.UserEntity;

import java.util.List;

public interface UserBookMarkService {

    void createUserBookmark(BookmarkRequestDto bookmarkRequestDto);

    void updateUserBookmark(BookmarkRequestDto bookmarkRequestDto);

    List<BookmarkDto> getUserBookmark(String userId);

    void deleteUserBookmark(String userId, String starId);
}
