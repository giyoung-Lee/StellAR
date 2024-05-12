package com.ssafy.stellar.fcm.service;

import com.ssafy.stellar.user.entity.UserEntity;
import org.springframework.stereotype.Service;

public interface FCMService {

    void sendNotification(String token, String title, String body);

    void saveDeviceToken(String deviceToken, UserEntity user);
}
