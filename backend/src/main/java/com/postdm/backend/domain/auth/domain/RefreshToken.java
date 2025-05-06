package com.postdm.backend.domain.auth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    private String username;

    @Lob
    private String refreshToken;

    private LocalDateTime expiration;

    public void update(String newToken, LocalDateTime newExpiration) {
        this.refreshToken = newToken;
        this.expiration = newExpiration;
    }
}