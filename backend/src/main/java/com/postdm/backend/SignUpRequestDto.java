package com.postdm.backend;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$") // 영문자, 특수문자, 숫자를 포함한 최소 8자 이상의 문자
    private String password;

    private String confirmPassword;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String certificationNumber;
}
