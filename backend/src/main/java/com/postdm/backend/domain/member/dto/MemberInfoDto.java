package com.postdm.backend.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

@Schema(description = "마이페이지 정보 DTO")
@Data
@Builder
public class MemberInfoDto {

    @Schema(description = "사용자 이름", example = "홍길동")
    private String nickname;

    @Schema(description = "사용자 이메일", example = "test1@test.com")
    @Email
    private String email;

    @Schema(description = "사용자 전화번호", example = "01012345678")
    private String phone;
}
