package com.postdm.backend.domain.member.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberInfoDto {

    private String nickname;

    @Email
    private String email;

    private String phone;
}
