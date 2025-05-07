package com.postdm.backend.domain.member.dto;

import com.postdm.backend.domain.member.domain.entity.MemberRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberPrincipalDto {

    private Long id;

    private String nickname;

    private String username;

}
