package com.postdm.backend.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberPrincipalDto {

    private Long id;

    @Schema(description = "사용자 이름", example = "홍길동")
    private String nickname;

    @Schema(description = "사용자 아이디", example = "test123")
    private String username;

}
