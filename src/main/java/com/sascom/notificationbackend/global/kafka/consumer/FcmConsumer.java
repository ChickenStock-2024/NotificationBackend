package com.sascom.notificationbackend.global.kafka.consumer;

import com.sascom.notificationbackend.error.code.NotificationErrorCode;
import com.sascom.notificationbackend.error.exception.TokenNotFoundException;
import com.sascom.notificationbackend.firebase.application.FcmSender;
import com.sascom.notificationbackend.global.dto.NotificationMessageDto;
import com.sascom.notificationbackend.token.entity.FcmToken;
import com.sascom.notificationbackend.token.repository.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmConsumer {

    private final FcmSender fcmSender;
    private final FcmTokenRepository fcmTokenRepository;

    @KafkaListener(
            topics = "${kafka.fcm-consumer.default-topic}",
            groupId = "${kafka.fcm-consumer.group-id}",
            containerFactory = "fcmKafkaListenerContainerFactory",
            concurrency = "3"
    )
    public void consumeFirebaseMessage(ConsumerRecord<String, NotificationMessageDto> messageDto) {
        long startTime = System.nanoTime();

        log.info("Success to reach Sender, Topic = Email");
        NotificationMessageDto nmd = messageDto.value();

        Long memberId = Long.parseLong(nmd.receiver()); // 멤버아이디로 변환
        FcmToken fcmToken = fcmTokenRepository.findByMemberId(memberId)
                .orElseThrow(() -> TokenNotFoundException.of(NotificationErrorCode.TOKEN_NOT_FOUND));
        String token = fcmToken.getToken();

        fcmSender.sendMessage(token, nmd.title(), nmd.body());
        log.info("Consumed Received FCM : {}}", nmd);

        // 종료 시간 기록
        long endTime = System.nanoTime();

        // 실행 시간 계산 (나노초 단위)
        long duration = endTime - startTime;

        // 실행 시간 출력 (초 단위로 변환)
        double executionTimeSec = (double) duration / 1000000000;
        System.out.printf("Execution time: %.6f seconds%n", executionTimeSec);
    }
}