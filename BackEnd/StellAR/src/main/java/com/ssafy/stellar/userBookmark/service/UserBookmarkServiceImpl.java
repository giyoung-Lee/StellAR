package com.ssafy.stellar.userBookmark.service;

import com.ssafy.stellar.userBookmark.dto.request.BookmarkRequestDto;
import com.ssafy.stellar.userBookmark.dto.response.BookmarkDto;
import com.ssafy.stellar.userBookmark.entity.UserBookmarkEntity;
import com.ssafy.stellar.userBookmark.repository.UserBookmarkRepository;
import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.star.repository.StarRepository;
import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserBookmarkServiceImpl implements UserBookMarkService {

    private final UserBookmarkRepository userBookmarkRepository;
    private final UserRepository userRepository;
    private final StarRepository starRepository;

    public UserBookmarkServiceImpl(
            UserBookmarkRepository userBookmarkRepository,
            UserRepository userRepository,
            StarRepository starRepository) {
        this.userBookmarkRepository = userBookmarkRepository;
        this.userRepository = userRepository;
        this.starRepository = starRepository;
    }

    @Override
    public void manageUserBookmark(BookmarkRequestDto bookmarkRequestDto, boolean isUpdate) {

        UserEntity user = validateUser(bookmarkRequestDto.getUserId());
        StarEntity star = validateStar(bookmarkRequestDto.getStarId());
        UserBookmarkEntity bookmark = userBookmarkRepository.findByUserAndStar(user, star);

        if (!isUpdate && bookmark != null) {
            throw new IllegalStateException("Bookmark already exists for given user and star");
        }

        if (isUpdate && bookmark == null) {
            throw new IllegalArgumentException("Bookmark not found for given user and star");
        }

        bookmark = bookmark == null ? new UserBookmarkEntity() : bookmark;
        bookmark.setUser(user);
        bookmark.setStar(star);
        bookmark.setBookmarkName(bookmarkRequestDto.getBookmarkName());

        userBookmarkRepository.save(bookmark);
    }

    private UserEntity validateUser(String userId) {
        UserEntity user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        return user;
    }

    private StarEntity validateStar(String starId) {
        StarEntity star = starRepository.findByStarId(starId);
        if (star == null) {
            throw new IllegalArgumentException("Star not found with id: " + starId);
        }
        return star;
    }


    @Override
    public List<BookmarkDto> getUserBookmark(String userId) {
        UserEntity user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with id: " + userId);
        }
        List<UserBookmarkEntity> allBookmarkByUser = userBookmarkRepository.findByUser(user);
        List<BookmarkDto> bookmarksDto = new ArrayList<>();

        for (UserBookmarkEntity bookmark : allBookmarkByUser) {
            BookmarkDto dto = new BookmarkDto();

            dto.setUserId(bookmark.getUser().getUserId());
            dto.setStarId(bookmark.getStar().getStarId());
            dto.setBookmarkName(bookmark.getBookmarkName());
            dto.setCraeteTime(bookmark.getCreateTime());
            bookmarksDto.add(dto);
        }

        return bookmarksDto;
    }

    @Override
    public void deleteUserBookmark(String userId, String starId) {
        UserEntity user = validateUser(userId);
        StarEntity star = validateStar(starId);

        UserBookmarkEntity bookmark = userBookmarkRepository.findByUserAndStar(user, star);
        if (bookmark == null) {
            throw new IllegalArgumentException("Bookmark not found for given user and star");
        }

        userBookmarkRepository.delete(bookmark);
    }
}
