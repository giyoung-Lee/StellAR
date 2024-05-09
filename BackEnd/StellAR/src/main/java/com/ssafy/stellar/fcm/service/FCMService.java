package com.ssafy.stellar.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.ssafy.stellar.user.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class FCMService {

    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;

    FCMService(FirebaseMessaging firebaseMessaging,
               UserRepository userRepository) {
        this.firebaseMessaging = firebaseMessaging;
        this.userRepository = userRepository;
    }

    public void sendNotification(String token, String title, String body) {
        try {
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(notification)
                    .build();

            firebaseMessaging.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
