package com.postdm.backend.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Schema(description = "아이디 찾기 DTO")
@Data
public class FindUsernameRequestDto {

    @Schema(description = "사용자 이메일", example = "test1@test.com")
    @NotBlank
    @Email
    private String email;

}
