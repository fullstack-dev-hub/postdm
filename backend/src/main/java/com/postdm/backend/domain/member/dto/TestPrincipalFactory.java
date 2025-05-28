package com.postdm.backend.domain.member.dto;

import com.postdm.backend.domain.member.domain.entity.Member;

public class TestPrincipalFactory {

    public static MemberPrincipalDto create(Member member) {
        return new MemberPrincipalDto(
                member.getId(),
                member.getNickname(),
                member.getUsername()
        );
    }
}