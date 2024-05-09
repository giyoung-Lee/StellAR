package com.ssafy.stellar.userBookmark.service;

import com.ssafy.stellar.userBookmark.dto.request.BookmarkRequestDto;
import com.ssafy.stellar.userBookmark.dto.response.BookmarkDto;
import com.ssafy.stellar.userBookmark.entity.UserBookmarkEntity;
import com.ssafy.stellar.userBookmark.repository.UserBookmarkRepository;
import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.star.repository.StarRepository;
import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.repository.UserRepository;
import com.ssafy.stellar.utils.stars.CalcStarLocation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserBookmarkServiceImpl implements UserBookMarkService {

    private final UserBookmarkRepository userBookmarkRepository;
    private final UserRepository userRepository;
    private final StarRepository starRepository;
    private final CalcStarLocation calc;

    public UserBookmarkServiceImpl(
            UserBookmarkRepository userBookmarkRepository,
            UserRepository userRepository,
            StarRepository starRepository,
            CalcStarLocation calc) {
        this.userBookmarkRepository = userBookmarkRepository;
        this.userRepository = userRepository;
        this.starRepository = starRepository;
        this.calc = calc;
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

    @Override
    public List<BookmarkDto> getUserBookmark(String userId) {
        UserEntity user = validateUser(userId);

        List<UserBookmarkEntity> allBookmarkByUser = userBookmarkRepository.findByUser(user);
        List<BookmarkDto> bookmarksDto = new ArrayList<>();

        for (UserBookmarkEntity bookmark : allBookmarkByUser) {
            BookmarkDto dto = getBookmarkDto(bookmark);
            bookmarksDto.add(dto);
        }

        return bookmarksDto;
    }

    @Override
    public BookmarkDto getUserBookmarkByStar(String userId, String starId) {
        UserEntity user = validateUser(userId);
        StarEntity star = validateStar(starId);
        UserBookmarkEntity bookmark = validateUserBookmark(user, star);

        return getBookmarkDto(bookmark);
    }

    @Override
    public void deleteUserBookmark(String userId, String starId) {
        UserEntity user = validateUser(userId);
        StarEntity star = validateStar(starId);
        UserBookmarkEntity bookmark = validateUserBookmark(user, star);

        userBookmarkRepository.delete(bookmark);
    }

    private BookmarkDto getBookmarkDto(UserBookmarkEntity bookmark) {

        StarEntity star = starRepository.findByStarId(bookmark.getStar().getStarId());
        double degreeDEC = calc.calculateNewDec(star.getDeclination(), Double.parseDouble(star.getPMDEC()));
        double hourRA = calc.calculateNewRA(star.getRA(), Double.parseDouble(star.getPMRA()));
        double normalizedMagV = calc.calculateNormalizedMagV(star);

        BookmarkDto dto = new BookmarkDto();

        dto.setUserId(bookmark.getUser().getUserId());
        dto.setStarId(bookmark.getStar().getStarId());
        dto.setBookmarkName(bookmark.getBookmarkName());
        dto.setCraeteTime(bookmark.getCreateTime());
        dto.setDegreeDEC(degreeDEC);
        dto.setHourRA(hourRA);
        dto.setNomalizedMagV(normalizedMagV);
        return dto;
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

    private UserBookmarkEntity validateUserBookmark(UserEntity user, StarEntity star) {
        UserBookmarkEntity bookmark = userBookmarkRepository.findByUserAndStar(user, star);
        if (bookmark == null) {
            throw new IllegalArgumentException("Bookmark not found for given user and star");
        }
        return bookmark;
    }
}
