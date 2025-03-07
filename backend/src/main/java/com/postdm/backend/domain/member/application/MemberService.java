package com.postdm.backend.domain.member.application;

import com.postdm.backend.domain.email.domain.entity.CertificationEntity;
import com.postdm.backend.domain.email.domain.repository.CertificationRepository;
import com.postdm.backend.domain.email.dto.CheckCertificationRequestDto;
import com.postdm.backend.domain.member.dto.FindUsernameRequestDto;
import com.postdm.backend.domain.member.domain.entity.Member;
import com.postdm.backend.domain.member.domain.repository.MemberRepository;
import com.postdm.backend.domain.member.dto.ResetPasswordRequestDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public boolean checkCertificationNumber(CheckCertificationRequestDto checkCertificationRequestDto) {
        String username  = checkCertificationRequestDto.getUsername();
        String email = checkCertificationRequestDto.getEmail();
        String certificationNumber = checkCertificationRequestDto.getCertificationNumber();

        CertificationEntity certificationEntity = certificationRepository.findByUsername(username);

        if(certificationEntity == null) {
            throw new IllegalArgumentException();
        }

        String encodedCertificationNumber = certificationEntity.getCertificationNumber();

        boolean isMatched = certificationEntity.getEmail().equals(email) && bCryptPasswordEncoder.matches(certificationNumber, encodedCertificationNumber);

        if(!isMatched) {
            throw new IllegalArgumentException();
        }

        return true;
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

        Member member = memberRepository.findByUsername(username);
        member.setPassword(encodedPassword);

        memberRepository.save(member);
        certificationRepository.delete(certificationEntity);

        return member;
    }
}
