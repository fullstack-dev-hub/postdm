package com.postdm.backend.domain.auth.dto;

import lombok.Data;

@Data
public class SignInRequestDto { // 로그인 데이터 전송을 위한 DTO

    private String username;

    private String password;

    private String email;

    private String certificationNumber;

}
