package com.postdm.backend.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "아이디 중복확인 DTO")
@NoArgsConstructor
public class IdCheckRequestDto { // 아이디 중복 확인 데이터 전송을 위한 DTO

    @Schema(description = "사용자 아이디", example = "test123")
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{7,15}$") // 영문자, 숫자를 포함한 7글자 이상 15글자 이하
    private String username;
}
