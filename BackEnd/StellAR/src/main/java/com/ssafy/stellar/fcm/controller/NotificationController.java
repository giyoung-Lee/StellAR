package com.ssafy.stellar.fcm.controller;

import com.ssafy.stellar.fcm.service.FCMServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




@Tag(name = "FCM", description = "푸시알림 기능")
@Slf4j
@RestController
@RequestMapping("/fcm")
public class NotificationController {

    private final FCMServiceImpl fcmService;

    public NotificationController(FCMServiceImpl fcmService) {
        this.fcmService = fcmService;
    }

    @Operation(summary = "푸시알림 테스트", description = "내 토큰과 타이틀, 바디를 넘겨주고 테스트를 합니다.")
    @ApiResponse(responseCode = "200", description = "푸시알림 테스트 성공!", content = @Content)
    @GetMapping("/send-notification-test")
    public HttpEntity<?> sendNotification(@RequestParam String title, @RequestParam String body) {
        fcmService.sendNotification(title, body);
        return new ResponseEntity<String>("Notification sent successfully", HttpStatus.OK);
    }
}
