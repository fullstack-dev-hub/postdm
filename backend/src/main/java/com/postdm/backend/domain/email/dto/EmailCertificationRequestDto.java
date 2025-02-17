package com.postdm.backend.domain.email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailCertificationRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;
}
