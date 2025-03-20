package com.postdm.backend.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "회원가입 DTO")
@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto { // 회원가입 데이터 전송을 위한 DTO

    @Schema(description = "사용자 이름", example = "홍길동")
    @NotBlank
    private String nickname;

    @Schema(description = "사용자 아이디", example = "test123")
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{7,15}$") // 영문자, 숫자를 포함한 7글자 이상 15글자 이하
    private String username;

    @Schema(description = "사용자 비밀번호", example = "test123")
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$") // 영문자, 특수문자, 숫자를 포함한 최소 8자 이상의 문자
    private String password;

    @Schema(description = "사용자 비밀번호 확인", example = "test123")
    private String confirmPassword;

    @Schema(description = "사용자 이메일", example = "test1@test.com")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "사용자 전화번호", example = "01012345678")
    @NotBlank
    private String phone;

    @Schema(description = "이메일 인증 번호", example = "0000")
    @NotBlank
    private String certificationNumber;
}
