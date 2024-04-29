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
    public void createUserBookmark(BookmarkRequestDto bookmarkRequestDto) {

        UserBookmarkEntity bookmark = new UserBookmarkEntity();

        UserEntity user = userRepository.findByUserId(bookmarkRequestDto.getUserId());
        StarEntity star = starRepository.findByStarId(bookmarkRequestDto.getStarId());
        String bookmarkName = bookmarkRequestDto.getBookmarkName();

        if (userBookmarkRepository.findByUserAndStar(user, star) != null) {
          bookmark = userBookmarkRepository.findByUserAndStar(user, star);
        } else {
            bookmark.setUser(user);
            bookmark.setStar(star);
        }

        bookmark.setBookmarkName(bookmarkName);

        userBookmarkRepository.save(bookmark);
    }

    @Override
    public void updateUserBookmark(BookmarkRequestDto bookmarkRequestDto) {

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

    }
}
