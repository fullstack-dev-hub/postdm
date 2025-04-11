package com.postdm.backend.domain.auth.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "token_blacklist")
public class TokenBlacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String token;

    private LocalDateTime expiration;

    private LocalDateTime createdAt = LocalDateTime.now();

    public TokenBlacklist(String token, LocalDateTime expiration) {
        this.token = token;
        this.expiration = expiration;
    }
}