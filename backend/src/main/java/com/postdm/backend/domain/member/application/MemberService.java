package com.postdm.backend.domain.member.application;

import com.postdm.backend.domain.email.domain.entity.CertificationEntity;
import com.postdm.backend.domain.email.domain.repository.CertificationRepository;
import com.postdm.backend.domain.email.dto.CheckCertificationRequestDto;
import com.postdm.backend.domain.member.dto.FindUsernameRequestDto;
import com.postdm.backend.domain.member.domain.entity.Member;
import com.postdm.backend.domain.member.domain.repository.MemberRepository;
import com.postdm.backend.domain.member.dto.MemberInfoDto;
import com.postdm.backend.domain.member.dto.ResetPasswordRequestDto;
import com.postdm.backend.global.common.exception.CustomException;
import com.postdm.backend.global.common.response.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final CertificationRepository certificationRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberService(MemberRepository memberRepository,  CertificationRepository certificationRepository,  BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.certificationRepository = certificationRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Member findUsernameByEmail(FindUsernameRequestDto findUsernameRequestDto) {
        Member member = memberRepository.findByEmail(findUsernameRequestDto.getEmail());

        if(member == null) {
            throw new IllegalArgumentException("존재하지 않는 메일 입니다.");
        }

        return member;
    }

    public Member resetPassword(ResetPasswordRequestDto resetPasswordRequestDto) {
        String username =  resetPasswordRequestDto.getUsername();
        String password = resetPasswordRequestDto.getPassword();
        String confirmPassword = resetPasswordRequestDto.getConfirmPassword();

        if(!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(password);

        CertificationEntity certificationEntity = certificationRepository.findByUsername(username);
        if(certificationEntity == null) {
            throw new IllegalArgumentException();
        }

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        member.setPassword(encodedPassword);

        memberRepository.save(member);
        certificationRepository.delete(certificationEntity);

        return member;
    }

    public MemberInfoDto loadMemberInfo(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return MemberInfoDto.builder().
                nickname(member.getNickname()).
                email(member.getEmail()).
                phone(member.getPhone()).
                build();
    }

    public void updateMemberInfo(String username, MemberInfoDto memberInfoDto) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        List<MemberInfoDto> list = new ArrayList<>();
        list.add(memberInfoDto);

        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getNickname() != null) {
                member.setNickname(list.get(i).getNickname());
            };
            if(list.get(i).getEmail() != null) {
                member.setEmail(list.get(i).getEmail());
            };
            if(list.get(i).getPhone() != null) {
                member.setPhone(list.get(i).getPhone());
            };
        }

        memberRepository.save(member);
    }
}
