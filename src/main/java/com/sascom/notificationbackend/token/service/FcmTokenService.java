package com.sascom.notificationbackend.token.service;

import com.sascom.notificationbackend.error.code.NotificationErrorCode;
import com.sascom.notificationbackend.error.exception.TokenNotFoundException;
import com.sascom.notificationbackend.token.entity.FcmToken;
import com.sascom.notificationbackend.token.repository.FcmTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class FcmTokenService {
    private final FcmTokenRepository fcmTokenRepository;

    public FcmTokenService(FcmTokenRepository fcmTokenRepository) {
        this.fcmTokenRepository = fcmTokenRepository;
    }

    public void saveToken(Long memberId, String token) {
        FcmToken fcmToken = new FcmToken();
        fcmToken.setMemberId(memberId);
        fcmToken.setToken(token);
        fcmTokenRepository.save(fcmToken);
    }

    public FcmToken getTokenByMemberId(Long memberId) {
        return fcmTokenRepository.findByMemberId(memberId)
                .orElseThrow(() -> TokenNotFoundException.of(NotificationErrorCode.TOKEN_NOT_FOUND));
    }
}