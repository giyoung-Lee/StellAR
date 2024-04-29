package com.ssafy.stellar.UserBookmark.controller;

import com.ssafy.stellar.UserBookmark.dto.request.BookmarkRequestDto;
import com.ssafy.stellar.UserBookmark.repository.UserBookmarkRepository;
import com.ssafy.stellar.UserBookmark.service.UserBookMarkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public HttpEntity<?> createBookmark(@ParameterObject @ModelAttribute BookmarkRequestDto bookmarkRequestDto) {

        try {
            userBookMarkService.createUserBookmark(bookmarkRequestDto);

            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("응 아니야", HttpStatus.BAD_REQUEST);
        }

    }

}
