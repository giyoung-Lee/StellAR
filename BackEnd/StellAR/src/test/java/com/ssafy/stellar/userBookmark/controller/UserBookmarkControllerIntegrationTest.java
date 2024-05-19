package com.ssafy.stellar.userBookmark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.stellar.userBookmark.dto.request.BookmarkRequestDto;
import com.ssafy.stellar.userBookmark.entity.UserBookmarkEntity;
import com.ssafy.stellar.userBookmark.repository.UserBookmarkRepository;
import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.repository.UserRepository;
import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.star.repository.StarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("User Star Bookmark Controller Integration Test")
@ActiveProfiles("mariadb")
public class UserBookmarkControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserBookmarkRepository userBookmarkRepository;

    @Autowired
    private StarRepository starRepository;

    @Autowired
    private UserRepository userRepository;

    private ObjectMapper objectMapper = new ObjectMapper();
    private UserEntity user;
    private StarEntity star;

    @BeforeEach
    void setup() {
        user = new UserEntity("bookmarkIntegrationTester", "password", null, null);
        userRepository.save(user);
        star = starRepository.findByStarId("Antares");
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get("C:/Users/user/Desktop/pjt3/.env"))) {
            stream.filter(line -> !line.startsWith("#") && line.contains("="))
                    .forEach(line -> {
                        String[] keyValue = line.split("=", 2);
                        System.out.println("Setting property: " + keyValue[0] + "=" + keyValue[1]);
                        registry.add(keyValue[0], () -> keyValue[1]);
                    });
        }
    }
    @Test
    @DisplayName("북마크 저장 테스트")
    @WithMockUser(username = "user", password = "password")
    void manageBookmarkSaveSuccess() throws Exception {
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(user.getUserId(), star.getStarId(), "My Bookmark");
        String bookmarkRequestDtoJson = objectMapper.writeValueAsString(bookmarkRequestDto);

        mockMvc.perform(post("/bookmark/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookmarkRequestDtoJson))
                .andExpect(status().isCreated());

        List<UserBookmarkEntity> bookmarks = userBookmarkRepository.findByUser(user);
        assertThat(bookmarks).hasSize(1);
        assertThat(bookmarks.get(0).getBookmarkName()).isEqualTo("My Bookmark");
    }

    @Test
    @DisplayName("북마크 수정 테스트")
    @WithMockUser(username = "user", password = "password")
    void manageBookmarkUpdateSuccess() throws Exception {
        UserBookmarkEntity bookmark = new UserBookmarkEntity();
        bookmark.setUser(user);
        bookmark.setStar(star);
        bookmark.setBookmarkName("Old Bookmark");
        userBookmarkRepository.save(bookmark);

        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(user.getUserId(), star.getStarId(), "Updated Bookmark");
        String bookmarkRequestDtoJson = objectMapper.writeValueAsString(bookmarkRequestDto);

        mockMvc.perform(put("/bookmark/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookmarkRequestDtoJson))
                .andExpect(status().isOk());

        List<UserBookmarkEntity> bookmarks = userBookmarkRepository.findByUser(user);
        assertThat(bookmarks).hasSize(1);
        assertThat(bookmarks.get(0).getBookmarkName()).isEqualTo("Updated Bookmark");
    }

    @Test
    @DisplayName("북마크 개별 조회 성공 테스트")
    @WithMockUser(username = "user", password = "password")
    void getBookmarkByStarSuccess() throws Exception {
        UserBookmarkEntity bookmark = new UserBookmarkEntity();
        bookmark.setUser(user);
        bookmark.setStar(star);
        bookmark.setBookmarkName("My Bookmark");
        userBookmarkRepository.save(bookmark);

        mockMvc.perform(get("/bookmark")
                        .param("userId", user.getUserId())
                        .param("starId", star.getStarId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookmarkName").value("My Bookmark"));
    }

    @Test
    @DisplayName("북마크 전체 조회 성공 테스트")
    @WithMockUser(username = "user", password = "password")
    void getBookmarkSuccess() throws Exception {
        UserBookmarkEntity bookmark1 = new UserBookmarkEntity();
        bookmark1.setUser(user);
        bookmark1.setStar(star);
        bookmark1.setBookmarkName("Bookmark 1");
        userBookmarkRepository.save(bookmark1);

        UserBookmarkEntity bookmark2 = new UserBookmarkEntity();
        bookmark2.setUser(user);
        bookmark2.setStar(star);
        bookmark2.setBookmarkName("Bookmark 2");
        userBookmarkRepository.save(bookmark2);

        mockMvc.perform(get("/bookmark/all")
                        .param("userId", user.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].bookmarkName").value("Bookmark 1"));
    }

    @Test
    @DisplayName("북마크 삭제 성공 테스트")
    @WithMockUser(username = "user", password = "password")
    void deleteBookmarkSuccess() throws Exception {
        UserBookmarkEntity bookmark = new UserBookmarkEntity();
        bookmark.setUser(user);
        bookmark.setStar(star);
        bookmark.setBookmarkName("My Bookmark");
        userBookmarkRepository.save(bookmark);

        mockMvc.perform(delete("/bookmark/delete")
                        .param("userId", user.getUserId())
                        .param("starId", star.getStarId()))
                .andExpect(status().isNoContent());

        List<UserBookmarkEntity> bookmarks = userBookmarkRepository.findByUser(user);
        assertThat(bookmarks).isEmpty();
    }

    // 실패 테스트 추가
    @Test
    @DisplayName("북마크 저장 실패 테스트 - 이미 있는 북마크")
    @WithMockUser(username = "user", password = "password")
    void manageBookmarkSaveFailure() throws Exception {
        UserBookmarkEntity bookmark = new UserBookmarkEntity();
        bookmark.setUser(user);
        bookmark.setStar(star);
        bookmark.setBookmarkName("My Bookmark");
        userBookmarkRepository.save(bookmark);

        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(user.getUserId(), star.getStarId(), "My Bookmark");
        String bookmarkRequestDtoJson = objectMapper.writeValueAsString(bookmarkRequestDto);

        mockMvc.perform(post("/bookmark/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookmarkRequestDtoJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("북마크 수정 실패 테스트 - 북마크 없음")
    @WithMockUser(username = "user", password = "password")
    void manageBookmarkUpdateFailure() throws Exception {
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(user.getUserId(), star.getStarId(), "Updated Bookmark");
        String bookmarkRequestDtoJson = objectMapper.writeValueAsString(bookmarkRequestDto);

        mockMvc.perform(put("/bookmark/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookmarkRequestDtoJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("북마크 개별 조회 실패 테스트 - 잘못된 데이터")
    @WithMockUser(username = "user", password = "password")
    void getBookmarkByStarFailure() throws Exception {
        mockMvc.perform(get("/bookmark")
                        .param("userId", user.getUserId())
                        .param("starId", "invalidStarId"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("북마크 전체 조회 실패 테스트 - 유저 없음")
    @WithMockUser(username = "user", password = "password")
    void getBookmarkFailure() throws Exception {
        mockMvc.perform(get("/bookmark/all")
                        .param("userId", "invalidUserId"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("북마크 삭제 실패 테스트 - 북마크 없음")
    @WithMockUser(username = "user", password = "password")
    void deleteBookmarkFailure() throws Exception {
        mockMvc.perform(delete("/bookmark/delete")
                        .param("userId", user.getUserId())
                        .param("starId", "invalidStarId"))
                .andExpect(status().isBadRequest());
    }
}
