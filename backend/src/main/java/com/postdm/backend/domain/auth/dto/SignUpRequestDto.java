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

    @NotBlank
    private String nickname;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{7,15}$") // 영문자, 숫자를 포함한 7글자 이상 15글자 이하
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$") // 영문자, 특수문자, 숫자를 포함한 최소 8자 이상의 문자
    private String password;

    private String confirmPassword;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private String certificationNumber;
}
