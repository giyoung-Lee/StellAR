package com.ssafy.stellar.fcm.controller;

import com.ssafy.stellar.fcm.service.FCMService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotificationController {

    private final FCMService fcmService;

    public NotificationController(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    @GetMapping("/send-notification")
    public String sendNotification(@RequestParam String token, @RequestParam String title, @RequestParam String body) {
        fcmService.sendNotification(token, title, body);
        return "Notification sent successfully";
    }
}
