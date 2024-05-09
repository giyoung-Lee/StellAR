package com.ssafy.stellar.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.ssafy.stellar.user.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class FcmService {

    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;

    FcmService (FirebaseMessaging firebaseMessaging,
                UserRepository userRepository) {
        this.firebaseMessaging = firebaseMessaging;
        this.userRepository = userRepository;
    }

    public String saveNotification(String token) {
        return null;
    }

}
