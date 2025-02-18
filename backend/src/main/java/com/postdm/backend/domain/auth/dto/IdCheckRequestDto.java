package com.postdm.backend.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IdCheckRequestDto { // 아이디 중복 확인 데이터 전송을 위한 DTO

    @NotBlank
    private String username;
}
