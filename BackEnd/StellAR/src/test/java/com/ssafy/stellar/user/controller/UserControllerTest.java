package com.ssafy.stellar.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.stellar.settings.TestSecurityConfig;
import com.ssafy.stellar.user.dto.request.SignUpDto;
import com.ssafy.stellar.user.dto.response.UserDto;
import com.ssafy.stellar.user.service.UserServiceImpl;
import com.ssafy.stellar.userBookmark.controller.UserBookmarkController;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(controllers = UserController.class)
@Import(TestSecurityConfig.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("User Controller Unit-Test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signUpSuccess() throws Exception {
        SignUpDto signUpDto = new SignUpDto("user1", "password", "김싸피", "M");
        String signUpDtoJson = objectMapper.writeValueAsString(signUpDto);
        doNothing().when(userService).signUp(any(SignUpDto.class));

        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signUpDtoJson).with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원가입 실패 테스트 - 잘못된 데이터")
    void signUpFailureBadRequest() throws Exception {
        SignUpDto signUpDto = new SignUpDto("user1", "password", "김싸피", "M");
        String signUpDtoJson = objectMapper.writeValueAsString(signUpDto);
        doThrow(new IllegalArgumentException("Invalid data provided")).when(userService).signUp(any(SignUpDto.class));

        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signUpDtoJson).with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 실패 테스트 - 서버 에러")
    void signUpFailureInternalServerError() throws Exception {
        SignUpDto signUpDto = new SignUpDto("user1", "password", "김싸피", "M");
        String signUpDtoJson = objectMapper.writeValueAsString(signUpDto);
        doThrow(new RuntimeException("Internal server error")).when(userService).signUp(any(SignUpDto.class));

        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signUpDtoJson).with(csrf()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("회원 탈퇴 성공 테스트")
    void deleteUserSuccess() throws Exception {
        doNothing().when(userService).deleteUser("user1", "password");

        mockMvc.perform(delete("/user")
                        .param("userId", "user1")
                        .param("password", "password").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("회원 탈퇴 실패 테스트 - 잘못된 비밀번호")
    void deleteUserFailureBadRequest() throws Exception {
        doThrow(new IllegalArgumentException("Invalid password")).when(userService).deleteUser("user1", "password");

        mockMvc.perform(delete("/user")
                        .param("userId", "user1")
                        .param("password", "password").with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원 탈퇴 실패 테스트 - 서버 에러")
    void deleteUserFailureInternalServerError() throws Exception {
        doThrow(new RuntimeException("Internal server error")).when(userService).deleteUser("user1", "password");

        mockMvc.perform(delete("/user")
                        .param("userId", "user1")
                        .param("password", "password").with(csrf()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccess() throws Exception {
        UserDto userDto = new UserDto("user1", "김싸피", "M");
        when(userService.logIn("user1", "password", null)).thenReturn(userDto);

        mockMvc.perform(post("/user/login")
                        .param("userId", "user1")
                        .param("password", "password")
                        .param("deviceToken", "")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 잘못된 데이터")
    void loginFailureBadRequest() throws Exception {
        when(userService.logIn("user1", "password", null)).thenThrow(new IllegalArgumentException("Invalid data provided"));

        mockMvc.perform(post("/user/login")
                        .param("userId", "user1")
                        .param("password", "password").with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 서버 에러")
    void loginFailureInternalServerError() throws Exception {
        when(userService.logIn("user1", "password", null)).thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(post("/user/login")
                        .param("userId", "user1")
                        .param("password", "password").with(csrf()))
                .andExpect(status().isInternalServerError());
    }
}
