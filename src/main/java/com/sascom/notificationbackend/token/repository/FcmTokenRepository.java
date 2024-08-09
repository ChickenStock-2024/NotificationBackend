package com.sascom.notificationbackend.token.repository;

import com.sascom.notificationbackend.token.entity.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> findByMemberId(Long memberId);
}
