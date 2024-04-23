package com.ssafy.stellar.user.controller;

import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "", description = "")
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService;}

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestParam String userId, @RequestParam String password) {

        UserEntity user = userService.logIn(userId, password);

        if (user != null) {
            return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("응 아니야", HttpStatus.BAD_REQUEST);
        }
    }
}
