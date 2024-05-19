package com.ssafy.stellar.fcm.service;

import com.google.firebase.messaging.*;
import com.ssafy.stellar.constellation.entity.ConstellationEventEntity;
import com.ssafy.stellar.constellation.repository.ConstellationEventRepository;
import com.ssafy.stellar.fcm.entity.DeviceTokenEntity;
import com.ssafy.stellar.fcm.repository.DeviceTokenRepository;
import jakarta.annotation.PostConstruct;
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
        List<String> tokens = deviceTokenRepository.findAllDeviceTokens();

        if (tokens.isEmpty()) {
            System.out.println("No tokens found.");
            return;
        }

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body.replace("\\n", "\n"))
                .build();

        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(tokens)
                .setNotification(notification)
                .build();

        try {
            BatchResponse response = firebaseMessaging.sendEachForMulticast(message);
            System.out.println("Successfully sent message: " + response.getSuccessCount() + " messages were sent successfully");

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

    @PostConstruct
    @Scheduled(cron = "0 0 0 * * ?")
    public void autoSetPushAlarm() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        String cutoffTime = "01:05";

        List<ConstellationEventEntity> events = constellationEventRepository.findEventsByDateAndTime(today, tomorrow, cutoffTime);
        for (ConstellationEventEntity event : events) {
            String title = event.getAstroEvent();

            String body = event.getAstroEvent() + chooseParticle(event.getAstroEvent()) + " 한 시간 뒤에 발생합니다." +
                    "\n" + "관측이 어려운 사람들은 stellAR에서 함께 확인해요!";
            // 스케줄링 시간 계산 (예: 이벤트 시간 1시간 전)
            ZonedDateTime eventTime = ZonedDateTime.of(event.getLocdate(), LocalTime.parse(event.getAstroTime()), ZoneId.systemDefault());
            Instant scheduledTime = eventTime.minusHours(1).toInstant();
            
            scheduler.schedule(() -> sendNotification(title, body), scheduledTime);
        }
    }

    public String chooseParticle(String word) {
        char lastChar = word.charAt(word.length() - 1); // 단어의 마지막 문자
        if ((lastChar - 0xAC00) % 28 > 0) {
            return "이"; // 받침이 있을 경우
        } else {
            return "가"; // 받침이 없을 경우
        }
    }
}
