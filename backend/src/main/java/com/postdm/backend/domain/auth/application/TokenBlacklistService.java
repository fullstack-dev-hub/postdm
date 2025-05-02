package com.postdm.backend.domain.auth.application;

import com.postdm.backend.domain.auth.domain.TokenBlacklist;
import com.postdm.backend.domain.auth.repository.TokenBlacklistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final TokenBlacklistRepository repository;

    public void save(String token, long expireMillis) {
        LocalDateTime expiration = Instant.ofEpochMilli(expireMillis)
                .atZone(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime();
        repository.save(new TokenBlacklist(token, expiration));
    }

    public boolean isBlacklisted(String token) {
        return repository.existsByToken(token);
    }

    // 중복 저장 방지
    public boolean saveIfNotExists(String token, LocalDateTime expireMillis) {
        if (repository.existsByToken(token)) {
            return false;
        }

        repository.save(new TokenBlacklist(token, expireMillis));
        return true;
    }

    // 만료된 토큰 정리
    @Scheduled(cron = "0 0 * * * *", zone = "Asia/Seoul") // 매시 정각
    @Transactional
    public void cleanExpiredTokens() {
        repository.deleteAllByExpirationBefore(LocalDateTime.now());
    }
}