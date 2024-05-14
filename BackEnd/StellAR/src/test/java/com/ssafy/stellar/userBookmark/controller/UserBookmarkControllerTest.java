package com.ssafy.stellar.userBookmark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.stellar.settings.TestSecurityConfig;
import com.ssafy.stellar.userBookmark.dto.request.BookmarkRequestDto;
import com.ssafy.stellar.userBookmark.dto.response.BookmarkDto;
import com.ssafy.stellar.userBookmark.service.UserBookmarkServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@WebMvcTest(controllers = UserBookmarkController.class)
@Import(TestSecurityConfig.class) // TestSecurityConfig를 import 해줘야 합니다.
@ExtendWith(MockitoExtension.class)
@DisplayName("User Star Bookmark Controller Unit-Test")
class UserBookmarkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserBookmarkServiceImpl userBookMarkService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("북마크 저장 테스트")
    void manageBookmarkSaveSuccess() throws Exception {
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto("user1", "star1", "My Bookmark");
        String bookmarkRequestDtoJson = objectMapper.writeValueAsString(bookmarkRequestDto);
        mockMvc.perform(post("/bookmark/create")
                        .with(httpBasic("user", "password")) // HTTP Basic 인증 추가
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookmarkRequestDtoJson).with(csrf()))
                .andExpect(status().isCreated());
    }
    @Test
    @DisplayName("북마크 수정 테스트")
    void manageBookmarkUpdateSuccess() throws Exception {
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto("user1", "star1", "My Bookmark");
        String bookmarkRequestDtoJson = objectMapper.writeValueAsString(bookmarkRequestDto);
        mockMvc.perform(put("/bookmark/create")
                        .with(httpBasic("user", "password")) // HTTP Basic 인증 추가
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookmarkRequestDtoJson).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("북마크 manage 실패 테스트 - 내부 서버 오류")
    void manageBookmarkSaveFailuer1() throws Exception {
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto("user1", "star1", "My Bookmark");
        String bookmarkRequestDtoJson = objectMapper.writeValueAsString(bookmarkRequestDto);
        doThrow(new RuntimeException("Internal server error")).when(userBookMarkService).manageUserBookmark(any(BookmarkRequestDto.class), eq(false));
        mockMvc.perform(post("/bookmark/create")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookmarkRequestDtoJson).with(csrf()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("북마크 저장 실패 테스트 - 이미 있는 북마크")
    void manageBookmarkSaveFailuer2() throws Exception {
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto("user1", "star1", "My Bookmark");
        String bookmarkRequestDtoJson = objectMapper.writeValueAsString(bookmarkRequestDto);
        doThrow(new IllegalStateException("Bookmark already exists for given user and star")).when(userBookMarkService).manageUserBookmark(any(BookmarkRequestDto.class), eq(false));

        mockMvc.perform(post("/bookmark/create")
                        .with(httpBasic("user", "password")) // HTTP Basic 인증 추가
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookmarkRequestDtoJson).with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("북마크 저장 실패 테스트 - 저장되지 않은 북마크")
    void manageBookmarkSaveFailuer3() throws Exception {
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto("user1", "star1", "My Bookmark");
        String bookmarkRequestDtoJson = objectMapper.writeValueAsString(bookmarkRequestDto);
        doThrow(new IllegalArgumentException("Bookmark not found for given user and star")).when(userBookMarkService).manageUserBookmark(any(BookmarkRequestDto.class), eq(true));
        mockMvc.perform(put("/bookmark/create")
                        .with(httpBasic("user", "password")) // HTTP Basic 인증 추가
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookmarkRequestDtoJson).with(csrf()))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("북마크 개별 조회 성공 테스트")
    void getBookmarkByStarSuccess() throws Exception {
        BookmarkDto bookmarkDto = new BookmarkDto("user1", "star1", "My Bookmark", LocalDateTime.now(), 0, 0, 0);
        when(userBookMarkService.getUserBookmarkByStar("user1", "star1")).thenReturn(bookmarkDto);

        mockMvc.perform(get("/bookmark")
                        .param("userId", "user1")
                        .param("starId", "star1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.starId").value("star1"))
                .andExpect(jsonPath("$.bookmarkName").value("My Bookmark"));
    }

    @Test
    @DisplayName("북마크 개별 조회 실패 테스트 - 잘못된 데이터")
    void getBookmarkByStarFailure() throws Exception {
        when(userBookMarkService.getUserBookmarkByStar("user1", "star1")).thenThrow(new IllegalArgumentException("Invalid data provided"));

        mockMvc.perform(get("/bookmark")
                        .param("userId", "user1")
                        .param("starId", "star1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("북마크 개별 조회 실패 테스트 - 서버 에러")
    void getBookmarkByStarFailureInternalServerError() throws Exception {
        when(userBookMarkService.getUserBookmarkByStar("user1", "star1")).thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(get("/bookmark")
                        .param("userId", "user1")
                        .param("starId", "star1"))
                .andExpect(status().isInternalServerError());
    }



    @Test
    @DisplayName("북마크 조회 성공 테스트")
    void getBookmarkSuccess() throws Exception{
        List<BookmarkDto> BookmarkListDto = new ArrayList<BookmarkDto>();
        BookmarkDto bookmarkDto = new BookmarkDto("user1", "star1", "My Bookmark", LocalDateTime.now(), 0, 0, 0);
        BookmarkListDto.add(bookmarkDto);
        when(userBookMarkService.getUserBookmark("user1")).thenReturn(BookmarkListDto);

        mockMvc.perform(get("/bookmark/all")
                        .param("userId", "user1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("북마크 조회 실패 테스트 - 서버 에러")
    void getBookmarkFailureInternalError() throws Exception{

        doThrow(new RuntimeException("Internal server error")).when(userBookMarkService).getUserBookmark("user1");
        mockMvc.perform(get("/bookmark/all")
                        .param("userId", "user1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("북마크 조회 실패 테스트 - 유저 없음")
    void getBookmarkFailureBadRequest() throws Exception{

        doThrow(new IllegalArgumentException("User not found with id: " + "user1")).when(userBookMarkService).getUserBookmark("user1");
        mockMvc.perform(get("/bookmark/all")
                        .param("userId", "user1"))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("북마크 삭제 성공 테스트")
    void deleteBookmarkSuccess() throws Exception {

        doNothing().when(userBookMarkService).deleteUserBookmark("user1", "star1");
        mockMvc.perform(delete("/bookmark/delete")
                        .param("userId", "user1")
                        .param("starId", "star1"))
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("북마크 삭제 실패 테스트 - 북마크 없음")
    void deleteBookmarkFailureBadRequest() throws Exception {

        doThrow(new IllegalArgumentException("Bookmark not found for given user and star")).when(userBookMarkService).deleteUserBookmark("user1", "star1");
        mockMvc.perform(delete("/bookmark/delete")
                        .param("userId", "user1")
                        .param("starId", "star1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("북마크 삭제 실패 테스트 - 서버 에러")
    void deleteBookmarkFailureInternalServerError() throws Exception {

        doThrow(new RuntimeException("Internal server error")).when(userBookMarkService).deleteUserBookmark("user1", "star1");
        mockMvc.perform(delete("/bookmark/delete")
                        .param("userId", "user1")
                        .param("starId", "star1"))
                .andExpect(status().isInternalServerError());
    }
}