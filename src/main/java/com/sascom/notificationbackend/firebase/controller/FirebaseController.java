package com.sascom.notificationbackend.firebase.controller;

import com.sascom.notificationbackend.firebase.application.FirebaseMessageSender;
import com.sascom.notificationbackend.global.dto.NotificationMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firebase")
@RequiredArgsConstructor
public class FirebaseController {
    private final FirebaseMessageSender messageSender;

    @PostMapping
    public ResponseEntity<?> messagingTest(@RequestBody NotificationMessageDto requestDto) {
        messageSender.sendMessage(requestDto.receiver(), requestDto.title(), requestDto.body());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> messageFront() {
        messageSender.sendMessage(
                "fdmWMBXKYaI446SYV27ZiB:APA91bHHSNpPhsnX89eHMRARsG6587lnlwUZtOccQpqZfSRXL58zmX6ar5f1LTfeovQIHurERRifMJHX-wlp5f6Orr2Km-RSw7OxxdcnUphOrKZUiegjqgnGUz64Up3rLhQkoVjp1a3x",
                "알림 타이틀22222",
                "축하드립니다 :)"
        );
        return ResponseEntity.ok().build();
    }
}
