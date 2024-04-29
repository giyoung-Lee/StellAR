package com.ssafy.stellar.userBookmark.controller;

import com.ssafy.stellar.userBookmark.dto.request.BookmarkRequestDto;
import com.ssafy.stellar.userBookmark.dto.response.BookmarkDto;
import com.ssafy.stellar.userBookmark.entity.UserBookmarkEntity;
import com.ssafy.stellar.userBookmark.repository.UserBookmarkRepository;
import com.ssafy.stellar.userBookmark.service.UserBookMarkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Bookmark", description = "별 북마크와 관련된 문서입니다.")
@RestController
@Slf4j
@RequestMapping("/bookmark")
public class UserBookmarkController {

    private final UserBookMarkService userBookMarkService;

    public UserBookmarkController(UserBookMarkService userBookMarkService) {
        this.userBookMarkService = userBookMarkService;
    }

    @PostMapping
//    @PutMapping   // 아직 수정 코드 미완성
    public HttpEntity<?> createBookmark(@ParameterObject @ModelAttribute BookmarkRequestDto bookmarkRequestDto) {

        try {
            userBookMarkService.createUserBookmark(bookmarkRequestDto);

            return new ResponseEntity<Void>(HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("응 아니야", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getBookmark(@RequestParam String userId) {
        try {
            List<BookmarkDto> userBookmarks = userBookMarkService.getUserBookmark(userId);
            return new ResponseEntity<List<BookmarkDto>>(userBookmarks, HttpStatus.OK);
        } catch (UsernameNotFoundException ex) {

            log.error("User not found", ex);
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            log.error("Internal server error", e);
            return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
