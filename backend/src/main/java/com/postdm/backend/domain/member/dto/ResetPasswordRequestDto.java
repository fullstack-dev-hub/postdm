package com.postdm.backend.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "비밀번호 재설정 DTO")
@Data
public class ResetPasswordRequestDto {

    @Schema(description = "사용자 아이디", example = "test123")
    @NotBlank
    private String username;

    @Schema(description = "새로운 비밀번호", example = "newpassword1")
    @NotBlank
    private String password;

    @Schema(description = "새로운 비밀번호 확인", example = "newpassword1")
    @NotBlank
    private String confirmPassword;

}
