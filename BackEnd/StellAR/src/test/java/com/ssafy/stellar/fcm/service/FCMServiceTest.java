package com.ssafy.stellar.fcm.service;

import com.google.firebase.messaging.*;
import com.ssafy.stellar.constellation.entity.ConstellationEventEntity;
import com.ssafy.stellar.constellation.repository.ConstellationEventRepository;
import com.ssafy.stellar.fcm.repository.DeviceTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("FCMServiceImpl Unit Tests")
public class FCMServiceTest {

    @InjectMocks
    private FCMServiceImpl fcmService;
    @Mock
    private FirebaseMessaging firebaseMessaging;
    @Mock
    private DeviceTokenRepository deviceTokenRepository;
    @Mock
    private ConstellationEventRepository constellationEventRepository;
    @Mock
    private TaskScheduler scheduler;



    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(fcmService, "scheduler", scheduler);
    }

    @Test
    @DisplayName("저장된 디바이스 토큰이 없을 때 푸시알림 작동 테스트")
    void sendNotificationNoTokensTest() throws FirebaseMessagingException {
        // Given
        String title = "Test Title";
        String body = "Test Body";
        when(deviceTokenRepository.findAllDeviceTokens()).thenReturn(Collections.emptyList());

        // When
        fcmService.sendNotification(title, body);

        // Then
        verify(firebaseMessaging, never()).sendEachForMulticast(any(MulticastMessage.class));
    }

    @Test
    @DisplayName("저장된 디바이스 토큰이 없을 때 푸시알림 작동 테스트")
    void sendNotificationWithTokensTest() throws FirebaseMessagingException {
        // Given
        String title = "Test Title";
        String body = "Test Body";
        List<String> tokens = List.of("token1", "token2");
        when(deviceTokenRepository.findAllDeviceTokens()).thenReturn(tokens);

        BatchResponse batchResponse = mock(BatchResponse.class);
        when(batchResponse.getSuccessCount()).thenReturn(tokens.size());
        SendResponse sendResponse = mock(SendResponse.class);
        when(sendResponse.isSuccessful()).thenReturn(true);
        when(batchResponse.getResponses()).thenReturn(Collections.nCopies(tokens.size(), sendResponse));
        when(firebaseMessaging.sendEachForMulticast(any(MulticastMessage.class))).thenReturn(batchResponse);

        // When
        fcmService.sendNotification(title, body);

        // Then
        verify(firebaseMessaging, times(1)).sendEachForMulticast(any(MulticastMessage.class));
    }

    @Test
    @DisplayName("푸시알림 실패 테스트 - FirebaseMessagingException이 발생할 때")
    void testSendNotificationThrowsException() throws FirebaseMessagingException {
        // Given
        String title = "Test Title";
        String body = "Test Body";
        List<String> tokens = List.of("token1", "token2");
        when(deviceTokenRepository.findAllDeviceTokens()).thenReturn(tokens);

        FirebaseMessagingException exception = mock(FirebaseMessagingException.class);
        when(firebaseMessaging.sendEachForMulticast(any(MulticastMessage.class))).thenThrow(exception);

        // When
        fcmService.sendNotification(title, body);

        // Then
        verify(firebaseMessaging, times(1)).sendEachForMulticast(any(MulticastMessage.class));
        verify(exception, times(1)).getMessage();
        verify(exception, times(1)).getMessagingErrorCode();
    }

    @Test
    @DisplayName("푸시알림 실패 테스트 - firebase의 응답이 실패라고 나올 때")
    void sendNotificationButFailTest() throws FirebaseMessagingException {
        // Given
        String title = "Test Title";
        String body = "Test Body";
        List<String> tokens = List.of("token1", "token2");
        when(deviceTokenRepository.findAllDeviceTokens()).thenReturn(tokens);

        BatchResponse batchResponse = mock(BatchResponse.class);
        when(batchResponse.getSuccessCount()).thenReturn(1); // Only one message is successful
        SendResponse successfulSendResponse = mock(SendResponse.class);
        when(successfulSendResponse.isSuccessful()).thenReturn(true);
        SendResponse unsuccessfulSendResponse = mock(SendResponse.class);
        when(unsuccessfulSendResponse.isSuccessful()).thenReturn(false);
        FirebaseMessagingException exception = mock(FirebaseMessagingException.class);
        when(unsuccessfulSendResponse.getException()).thenReturn(exception);
        when(exception.getMessage()).thenReturn("Failed to send message");
        when(batchResponse.getResponses()).thenReturn(List.of(successfulSendResponse, unsuccessfulSendResponse));
        when(firebaseMessaging.sendEachForMulticast(any(MulticastMessage.class))).thenReturn(batchResponse);

        // When
        fcmService.sendNotification(title, body);

        // Then
        verify(firebaseMessaging, times(1)).sendEachForMulticast(any(MulticastMessage.class));
        verify(unsuccessfulSendResponse, times(1)).isSuccessful();
        verify(unsuccessfulSendResponse, times(1)).getException();
        verify(exception, times(1)).getMessage();
    }

    @Test
    @DisplayName("12시 스케쥴링 작업중 오늘 이벤트가 있을 때")
    void autoSetPushAlarmWithEventsTest() {
        // Given
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        ConstellationEventEntity event = new ConstellationEventEntity();
        event.setAstroEvent("Test Event");
        event.setAstroTime("20:00");
        event.setLocdate(today);
        List<ConstellationEventEntity> todayEvents = new ArrayList<>();
        todayEvents.add(event);
        when(constellationEventRepository.findEventsByDateAndTime(any(LocalDate.class), any(LocalDate.class), anyString()))
                .thenReturn(todayEvents);

        // When
        fcmService.autoSetPushAlarm();

        // Then
        verify(scheduler, times(1)).schedule(any(Runnable.class), any(Instant.class));
    }

    @Test
    @DisplayName("12시 스케쥴링 작업중 오늘 이벤트가 없을 때")
    void autoSetPushAlarmWithNoEventsTest() {
        // Given

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        String cutoffTime = "01:05";

        when(constellationEventRepository.findEventsByDateAndTime(today, tomorrow, cutoffTime))
                .thenReturn(Collections.emptyList());

        // When
        fcmService.autoSetPushAlarm();

        // Then
        verify(scheduler, never()).schedule(any(Runnable.class), any(Instant.class));
    }

    @Test
    @DisplayName("알림 별 조사 설정 테스트")
    void testChooseParticle() {
        // Act & Assert
        assertThat(fcmService.chooseParticle("별")).isEqualTo("이");
        assertThat(fcmService.chooseParticle("지구")).isEqualTo("가");
    }
}
