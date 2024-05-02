package com.ssafy.stellar.user.controller;

import com.ssafy.stellar.user.dto.request.SignUpDto;
import com.ssafy.stellar.user.dto.response.UserDto;
import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "사용자 정보와 관련된 문서입니다.")
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService;}

    @Operation(summary = "회원가입", description = "사용자가 회원가입을 합니다.")
    @ApiResponse(responseCode = "201", description = "회원가입 성공", content = @Content)
    @ApiResponse(responseCode = "400", description = "회원가입 실패", content = @Content)
    @PostMapping("/signup")
    public HttpEntity<?> signUp(@ParameterObject @ModelAttribute SignUpDto user) {
        try {
            userService.signUp(user);

            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("응 아니야", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "회원 탈퇴", description = "사용자가 회원탈퇴를 합니다.")
    @ApiResponse(responseCode = "204", description = "회원탈퇴 성공", content = @Content)
    @ApiResponse(responseCode = "400", description = "잘못된 비밀번호", content = @Content)
    @DeleteMapping
    public HttpEntity<?> deleteUser(@RequestParam String userId, @RequestParam String password) {
        if (userService.checkPassword(userId, password)) {
            userService.deleteUser(userId);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<String>("Invalid password", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자가 로그인을 합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = UserDto.class)))
    @ApiResponse(responseCode = "400", description = "로그인 실패", content = @Content)
    public HttpEntity<?> login(@RequestParam String userId, @RequestParam String password) {

        UserDto user = userService.logIn(userId, password);

        if (user != null) {
            return new ResponseEntity<UserDto>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("응 아니야", HttpStatus.BAD_REQUEST);
        }
    }

}
