package com.postdm.backend.domain.email.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "이메일 인증 DTO")
@Getter
@Setter
@NoArgsConstructor
public class EmailCertificationRequestDto { // 이메일 인증 데이터를 전송하기 위한 DTO

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;
}
