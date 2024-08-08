package com.sascom.notificationbackend.global.kafka.producer;

import com.sascom.notificationbackend.global.dto.NotificationMessageDto;
import com.sascom.notificationbackend.global.kafka.properties.EmailConsumerProperties;
import com.sascom.notificationbackend.global.kafka.properties.FcmConsumerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

// KafkaProducer
/* 바라보고 있는 kafka broker topic 메세지 발행 */
@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProducer {

    private final EmailConsumerProperties emailConsumerProperties;
    private final FcmConsumerProperties fcmConsumerProperties;
    private final KafkaTemplate<String, NotificationMessageDto> kafkaTemplate;

    public void sendEmailMessageToKafka(NotificationMessageDto message) {
        log.info("Producer Send Email Message : {}",message);
        this.kafkaTemplate.send(emailConsumerProperties.defaultTopic(),message);
    }

    public void sendAlarmMessageToKafka(NotificationMessageDto message) {
        log.info("Producer Send Alarm Message : {}",message);
        this.kafkaTemplate.send(fcmConsumerProperties.defaultTopic(),message);
    }
}