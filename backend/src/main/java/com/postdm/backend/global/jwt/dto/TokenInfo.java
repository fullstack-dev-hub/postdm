package com.postdm.backend.global.jwt.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenInfo {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private String role;

    @Builder
    public TokenInfo(String grantType, String accessToken, String refreshToken, String role) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = role;
    }
}
