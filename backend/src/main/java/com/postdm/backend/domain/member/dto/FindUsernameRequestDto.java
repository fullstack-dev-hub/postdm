package com.postdm.backend.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class FindUsernameRequestDto {

    @NotBlank
    @Email
    private String email;

}
