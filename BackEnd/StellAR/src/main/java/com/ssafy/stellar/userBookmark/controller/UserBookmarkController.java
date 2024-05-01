package com.ssafy.stellar.userBookmark.controller;

import com.ssafy.stellar.userBookmark.dto.request.BookmarkRequestDto;
import com.ssafy.stellar.userBookmark.dto.response.BookmarkDto;
import com.ssafy.stellar.userBookmark.service.UserBookMarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.*;
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

    @Operation(summary = "유저 별마크 저장/수정", description = "POST: 별마크를 저장합니다. / PUT: 별마크를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "별마크 수정 성공", content = @Content)
    @ApiResponse(responseCode = "201", description = "별마크 저장 성공", content = @Content)
    @ApiResponse(responseCode = "400", description = "별마크 수정/저장 실패", content = @Content)
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> manageBookmark(@ParameterObject @ModelAttribute BookmarkRequestDto bookmarkRequestDto, HttpServletRequest request) {
        try {
            boolean isUpdate = request.getMethod().equals("PUT");

            userBookMarkService.manageUserBookmark(bookmarkRequestDto, isUpdate);
            HttpStatusCode status;

            if (isUpdate) {
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.CREATED;
            }

            return new ResponseEntity<Void>(status);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "유저 별마크 조회", description = "사용자가 저장한 별마크를 조회합니다. 별마크 리스트 출력")
    @ApiResponse(responseCode = "200", description = "별마크 조회 성공", content = @Content)
    @ApiResponse(responseCode = "404", description = "유저 정보 없음", content = @Content)
    @GetMapping
    public ResponseEntity<?> getBookmark(@RequestParam String userId) {
        try {
            List<BookmarkDto> userBookmarks = userBookMarkService.getUserBookmark(userId);
            return new ResponseEntity<List<BookmarkDto>>(userBookmarks, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {

            log.error("User not found", e);
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            log.error("Internal server error", e);
            return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "별마크 삭제", description = "사용자가 저장한 별마크를 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "별마크 삭제", content = @Content)
    @ApiResponse(responseCode = "400", description = "요청 데이터 에러", content = @Content)
    @DeleteMapping
    public ResponseEntity<?> deleteBookmark(@RequestParam String userId, @RequestParam String starId) {
        try {
            userBookMarkService.deleteUserBookmark(userId, starId);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
