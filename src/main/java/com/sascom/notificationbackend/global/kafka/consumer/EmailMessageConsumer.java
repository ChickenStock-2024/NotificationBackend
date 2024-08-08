package com.sascom.notificationbackend.global.kafka.consumer;

import com.sascom.notificationbackend.email.application.EmailSender;
import com.sascom.notificationbackend.global.dto.NotificationMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailMessageConsumer {

    private final EmailSender emailSender;

    @KafkaListener(topics = "email", groupId = "email_consumer", containerFactory = "kafkaListenerContainerFactory")
    public void consumeEmailMessage(ConsumerRecord<String, NotificationMessageDto> messageDto) {
        long startTime = System.nanoTime();

        System.out.println("Success to reach Sender, Topic = Email");
        System.out.printf("messageDto = %s\n", messageDto);
        NotificationMessageDto nmd = messageDto.value();
        emailSender.sendMessage(nmd.receiver(), nmd.title(), nmd.body());
        System.out.printf("Consumed Received Email Message : %s%n", nmd);

        // 종료 시간 기록
        long endTime = System.nanoTime();

        // 실행 시간 계산 (나노초 단위)
        long duration = endTime - startTime;

        // 실행 시간 출력 (밀리초 단위로 변환)
        System.out.println("Execution time: " + (duration) + " nanoseconds");
    }

}