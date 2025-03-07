package com.postdm.backend.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "로그인 DTO")
@Data
public class SignInRequestDto { // 로그인 데이터 전송을 위한 DTO

    private String username;

    private String password;

}
