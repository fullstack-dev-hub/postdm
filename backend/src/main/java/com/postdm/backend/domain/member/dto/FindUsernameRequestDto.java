package com.postdm.backend.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Schema(description = "아이디 찾기 DTO")
@Data
public class FindUsernameRequestDto {

    @NotBlank
    @Email
    private String email;

}
