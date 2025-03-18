package com.postdm.backend.global.jwt.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenInfo { // 토큰 전송을 위한 DTO
    private String grantType;
    private String accessToken;
    private String role;

    @Builder
    public TokenInfo(String grantType, String accessToken, String role) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.role = role;
    }
}
