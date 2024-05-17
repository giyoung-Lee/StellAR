package com.ssafy.stellar.userConstellation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.stellar.settings.TestSecurityConfig;
import com.ssafy.stellar.userConstellation.dto.request.UserConstellationRequestDto;
import com.ssafy.stellar.userConstellation.dto.response.UserConstellationDto;
import com.ssafy.stellar.userConstellation.dto.response.UserConstellationLinkDto;
import com.ssafy.stellar.userConstellation.service.UserConstellationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@WebMvcTest(controllers = UserConstellationController.class)
@Import(TestSecurityConfig.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("User Constellation Controller Unit-Test")
public class UserConstellationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserConstellationServiceImpl userConstellationService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<List<String>> links;
    private List<UserConstellationLinkDto> userLink;
    @BeforeEach
    void setUp() {
        links = new ArrayList<>();
        List<String> link1 = new ArrayList<>();
        link1.add("star1");
        link1.add("star2");

        List<String> link2 = new ArrayList<>();
        link2.add("star2");
        link2.add("star3");

        links.add(link1);
        links.add(link2);

        userLink = new ArrayList<>();
        UserConstellationLinkDto userConstellationLinkDto1 = new UserConstellationLinkDto(1L, "star1", "star2");
        UserConstellationLinkDto userConstellationLinkDto2 = new UserConstellationLinkDto(1L, "star2", "star3");
        userLink.add(userConstellationLinkDto1);
        userLink.add(userConstellationLinkDto2);
    }

    @Test
    @DisplayName("유저 별자리 저장 성공 테스트")
    void createUserConstellationSuccessTest() throws Exception {
        UserConstellationRequestDto userConstellationRequestDto = new UserConstellationRequestDto("wncks", null, "주찬", "설명", links);
        String userConstellationRequestDtoJson = objectMapper.writeValueAsString(userConstellationRequestDto);
        mockMvc.perform(post("/user-constellation/create")
                .with(httpBasic("user", "password")) // HTTP Basic 인증 추가
                .contentType(MediaType.APPLICATION_JSON)
                .content(userConstellationRequestDtoJson).with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("유저 별자리 수정 성공 테스트")
    void updateUserConstellationSuccessTest() throws Exception {
        UserConstellationRequestDto userConstellationRequestDto = new UserConstellationRequestDto("wncks", 1L, "주찬", "설명", links);
        String userConstellationRequestDtoJson = objectMapper.writeValueAsString(userConstellationRequestDto);
        mockMvc.perform(put("/user-constellation/create")
                        .with(httpBasic("user", "password")) // HTTP Basic 인증 추가
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userConstellationRequestDtoJson).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("북마크 manage 실패 테스트 - 내부 서버 에러")
    void manageBookmarkSaveFailTest1() throws Exception {
        UserConstellationRequestDto userConstellationRequestDto = new UserConstellationRequestDto("wncks", null, "주찬", "설명", links);
        String userConstellationRequestDtoJson = objectMapper.writeValueAsString(userConstellationRequestDto);
        doThrow(new RuntimeException("Internal server error")).when(userConstellationService).manageUserConstellation(any(UserConstellationRequestDto.class), eq(false));
        mockMvc.perform(post("/user-constellation/create")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userConstellationRequestDtoJson).with(csrf()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("북마크 manage 실패 테스트 - post 요청시 별자리 별자리 Id 데이터 입력")
    void manageBookmarkSaveFailTest2() throws Exception {
        UserConstellationRequestDto userConstellationRequestDto = new UserConstellationRequestDto("wncks", 1L, "주찬", "설명", links);
        String userConstellationRequestDtoJson = objectMapper.writeValueAsString(userConstellationRequestDto);
        doThrow(new IllegalStateException("Creating constellation don't need constellation Id.")).when(userConstellationService).manageUserConstellation(any(UserConstellationRequestDto.class), eq(false));
        mockMvc.perform(post("/user-constellation/create")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userConstellationRequestDtoJson).with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("북마크 manage 실패 테스트 - put 요청시 없는 별자리 수정 요청")
    void manageBookmarkSaveFailTest3() throws Exception {
        UserConstellationRequestDto userConstellationRequestDto = new UserConstellationRequestDto("wncks", 1L, "주찬", "설명", links);
        String userConstellationRequestDtoJson = objectMapper.writeValueAsString(userConstellationRequestDto);
        doThrow(new IllegalStateException("User Constellation not found for given user and constellationId.")).when(userConstellationService).manageUserConstellation(any(UserConstellationRequestDto.class), eq(true));
        mockMvc.perform(put("/user-constellation/create")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userConstellationRequestDtoJson).with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유저 별자리 개별 조회 성공 테스트")
    void getUserConstellationByIdSuccessTest() throws Exception {
        UserConstellationDto userConstellationDto = new UserConstellationDto(1L, "주찬", "설명", LocalDateTime.now(), userLink, 0.0, 0.0, 10000.0);
        when(userConstellationService.getUserConstellationById("wncks", 1L)).thenReturn(userConstellationDto);

        mockMvc.perform(get("/user-constellation")
                .param("userId", "wncks")
                .param("constellationId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("주찬"));
    }

    @Test
    @DisplayName("유저 별자리 개별 조회 실패 테스트 - 내부 서버 에러")
    void getUserConstellationByIdFailTest1() throws Exception {
        UserConstellationDto userConstellationDto = new UserConstellationDto(1L, "주찬", "설명", LocalDateTime.now(), userLink, 0.0, 0.0, 10000.0);
        doThrow(new RuntimeException("Internal server error")).when(userConstellationService).getUserConstellationById(anyString(), anyLong());
        mockMvc.perform(get("/user-constellation")
                        .param("userId", "wncks")
                        .param("constellationId", "10"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("유저 별자리 개별 조회 실패 테스트 - 유저 없음")
    void getUserConstellationByIdFailTest2() throws Exception {
        doThrow(new IllegalArgumentException("User not found with id: " + "wncks")).when(userConstellationService).getUserConstellationById(anyString(), anyLong());
        mockMvc.perform(get("/user-constellation")
                        .param("userId", "wncks")
                        .param("constellationId", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유저 별자리 전체 조회 성공 테스트")
    void getUserConstellationSuccessTest() throws Exception {
        List<UserConstellationDto> userConstellationDtos = new ArrayList<UserConstellationDto>();
        UserConstellationDto userConstellationDto = new UserConstellationDto(1L, "주찬", "설명", LocalDateTime.now(), userLink, 0.0, 0.0, 10000.0);
        userConstellationDtos.add(userConstellationDto);
        when(userConstellationService.getUserConstellation("wncks")).thenReturn(userConstellationDtos);
        mockMvc.perform(get("/user-constellation/all")
                        .param("userId", "wncks"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("유저 별자리 전체 조회 실패 테스트 - 내부 서버 에러")
    void getUserConstellationFailTest1() throws Exception {
        doThrow(new RuntimeException("Internal server error")).when(userConstellationService).getUserConstellation(anyString());
        mockMvc.perform(get("/user-constellation/all")
                        .param("userId", "wncks"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("유저 별자리 전체 조회 실패 테스트 - 잘못된 데이터")
    void getUserConstellationFailTest2() throws Exception {
        doThrow(new IllegalArgumentException("User not found with id: " + "wncks")).when(userConstellationService).getUserConstellation(anyString());
        mockMvc.perform(get("/user-constellation/all")
                        .param("userId", "wncks"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유저 별자리 링크 조회 성공 테스트")
    void getUserConstellationLinkSuccessTest() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("1", links); // 예시 데이터 추가
        when(userConstellationService.getUserConstellationLink("wncks")).thenReturn(map);

        mockMvc.perform(get("/user-constellation/link")
                        .param("userId", "wncks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['1']").isArray())
                .andExpect(jsonPath("$['1'][0]").isArray())
                .andExpect(jsonPath("$['1'][0][0]").value("star1"))
                .andExpect(jsonPath("$['1'][0][1]").value("star2"))
                .andExpect(jsonPath("$['1'][1][0]").value("star2"))
                .andExpect(jsonPath("$['1'][1][1]").value("star3"));
    }

    @Test
    @DisplayName("유저 별자리 링크 조회 실패 테스트 - 내부 서버 에러")
    void getUserConstellationLinkFailTest1() throws Exception {
        doThrow(new RuntimeException("Internal server error")).when(userConstellationService).getUserConstellationLink(anyString());
        mockMvc.perform(get("/user-constellation/link")
                        .param("userId", "wncks"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("유저 별자리 링크 조회 실패 테스트 - 잘못된 데이터")
    void getUserConstellationLinkFailTest2() throws Exception {
        doThrow(new IllegalArgumentException("User not found with id: " + "wncks")).when(userConstellationService).getUserConstellationLink(anyString());
        mockMvc.perform(get("/user-constellation/link")
                        .param("userId", "wncks"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유저 별자리 삭제 성공 테스트")
    void deleteUserConstellationSuccessTest() throws Exception {
        doNothing().when(userConstellationService).deleteUserConstellation("wncks", 1L);
        mockMvc.perform(delete("/user-constellation/delete")
                .param("userId", "wncks")
                .param("constellationId", "1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("유저 별자리 삭제 실패 테스트 - 내부 서버 에러")
    void deleteUserConstellationFailTest1() throws Exception {
        doThrow(new RuntimeException("Internal server error")).when(userConstellationService).deleteUserConstellation(anyString(), anyLong());
        mockMvc.perform(delete("/user-constellation/delete")
                        .param("userId", "wncks")
                        .param("constellationId", "1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("유저 별자리 삭제 실패 테스트 - 잘못된 데이터")
    void deleteUserConstellationFailTest2() throws Exception {
        doThrow(new IllegalArgumentException("User not found with id: " + "wncks")).when(userConstellationService).deleteUserConstellation(anyString(), anyLong());
        mockMvc.perform(delete("/user-constellation/delete")
                        .param("userId", "wncks")
                        .param("constellationId", "1"))
                .andExpect(status().isBadRequest());
    }
}
