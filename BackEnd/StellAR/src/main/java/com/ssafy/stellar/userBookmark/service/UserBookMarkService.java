package com.ssafy.stellar.userBookmark.service;

import com.ssafy.stellar.userBookmark.dto.request.BookmarkRequestDto;
import com.ssafy.stellar.userBookmark.dto.response.BookmarkDto;

import java.util.List;

public interface UserBookMarkService {

    void manageUserBookmark(BookmarkRequestDto bookmarkRequestDto, boolean isUpdate);

    List<BookmarkDto> getUserBookmark(String userId);

    BookmarkDto getUserBookmarkByStar(String userId, String starId);

    void deleteUserBookmark(String userId, String starId);
}
