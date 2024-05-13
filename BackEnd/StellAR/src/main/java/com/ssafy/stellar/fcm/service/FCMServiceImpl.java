package com.ssafy.stellar.fcm.service;

import com.google.firebase.messaging.*;
import com.ssafy.stellar.constellation.entity.ConstellationEventEntity;
import com.ssafy.stellar.constellation.repository.ConstellationEventRepository;
import com.ssafy.stellar.fcm.entity.DeviceTokenEntity;
import com.ssafy.stellar.fcm.repository.DeviceTokenRepository;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FCMServiceImpl {

    private final TaskScheduler scheduler;
    private final FirebaseMessaging firebaseMessaging;
    private final DeviceTokenRepository deviceTokenRepository ;

    private final ConstellationEventRepository constellationEventRepository;

    FCMServiceImpl(FirebaseMessaging firebaseMessaging,
                   DeviceTokenRepository deviceTokenRepository,
                   ConstellationEventRepository constellationEventRepository) {

        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        this.scheduler = scheduler;

        this.firebaseMessaging = firebaseMessaging;
        this.deviceTokenRepository = deviceTokenRepository;
        this.constellationEventRepository = constellationEventRepository;
    }

    public void sendNotification(String title, String body) {
        List<String> tokens = deviceTokenRepository.findAll().stream()
                .map(DeviceTokenEntity::getDeviceToken)
                .collect(Collectors.toList());

        if (tokens.isEmpty()) {
            System.out.println("No tokens found.");
            return;
        }

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(tokens)
                .setNotification(notification)
                .build();

        try {
            BatchResponse response = firebaseMessaging.sendEachForMulticast(message);
            System.out.println("Successfully sent message: " + response.getSuccessCount() + " messages were sent successfully");

            // Optionally, handle the responses for each message
            for (SendResponse resp : response.getResponses()) {
                if (!resp.isSuccessful()) {
                    System.err.println("Failed to send message: " + resp.getException().getMessage());
                }
            }
        } catch (FirebaseMessagingException e) {
            System.err.println("Failed to send multicast message: " + e.getMessage());
            System.err.println("Error code: " + e.getMessagingErrorCode());
        }
    }

    public void scheduleMessage(Instant when, String title, String body) {
        scheduler.schedule(() -> sendNotification(title, body), when);
    }
    // 매일 특정 시간에 실행

    public void autoSetPushAlarm() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        String cutoffTime = "01:05";

        List<ConstellationEventEntity> events = constellationEventRepository.findEventsByDateAndTime(today, tomorrow, cutoffTime);
        for (ConstellationEventEntity event : events) {
            String token = "your_device_token"; // 토큰 가져오는 로직 필요
            String title = event.getAstroEvent();
            String body = "Don't forget your event at " + event.getAstroTime();

            // 스케줄링 시간 계산 (예: 이벤트 시간 1시간 전)
            ZonedDateTime eventTime = ZonedDateTime.of(event.getLocdate(), LocalTime.parse(event.getAstroTime()), ZoneId.systemDefault());
            Instant scheduledTime = eventTime.minusHours(1).toInstant();

            scheduler.schedule(() -> sendNotification(title, body), scheduledTime);
        }
    }
}
