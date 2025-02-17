package com.postdm.backend.domain.auth.dto;

import lombok.Data;

@Data
public class SignInRequestDto {

    private String username;

    private String password;

    private String email;

    private String certificationNumber;

}
