package com.postdm.backend.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "아이디 중복확인 DTO")
@NoArgsConstructor
public class IdCheckRequestDto { // 아이디 중복 확인 데이터 전송을 위한 DTO

    @NotBlank
    private String username;
}
