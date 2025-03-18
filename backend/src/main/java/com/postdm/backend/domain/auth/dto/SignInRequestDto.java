package com.postdm.backend.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "로그인 DTO")
@Data
public class SignInRequestDto { // 로그인 데이터 전송을 위한 DTO

    @Schema(description = "사용자 아이디", example = "test123")
    @NotBlank
    private String username;

    @Schema(description = "사용자 비밀번호", example = "test123")
    @NotBlank
    private String password;

}
