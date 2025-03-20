package com.postdm.backend.global.jwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "JWT 토큰 DTO")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenInfo { // 토큰 전송을 위한 DTO

    @Schema(description = "grantType")
    private String grantType;

    @Schema(description = "AccessToken 값")
    private String accessToken;

    @Schema(description = "해당 사용자의 role")
    private String role;

    @Builder
    public TokenInfo(String grantType, String accessToken, String role) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.role = role;
    }
}
