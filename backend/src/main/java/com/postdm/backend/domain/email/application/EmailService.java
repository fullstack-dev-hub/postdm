package com.postdm.backend.domain.email.application;

import com.postdm.backend.domain.email.domain.entity.CertificationEntity;
import com.postdm.backend.domain.email.domain.repository.CertificationRepository;
import com.postdm.backend.domain.email.dto.CheckCertificationRequestDto;
import com.postdm.backend.domain.email.dto.EmailCertificationRequestDto;
import com.postdm.backend.domain.member.domain.repository.MemberRepository;
import com.postdm.backend.global.common.exception.CustomException;
import com.postdm.backend.global.common.response.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmailService { // 이메일 관련 서비스

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailProvider emailProvider;

    public void emailCertification(EmailCertificationRequestDto emailCertificationRequestDto) { // 인증메일 전송 서비스

        String username = emailCertificationRequestDto.getUsername();
        String email = emailCertificationRequestDto.getEmail();

        boolean existedEmail = memberRepository.existsByEmail(email);
        if (existedEmail) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }

        String certificationNumber = CertificationNumber.getCertificationNumber();

        boolean isSucceed = emailProvider.sendCertificationMail(email, certificationNumber);

        if (!isSucceed) {
            throw new CustomException(ErrorCode.EMAIL_SEND_FAILED);
        }

        CertificationEntity certificationEntity = new CertificationEntity(username, email, bCryptPasswordEncoder.encode(certificationNumber));

        certificationRepository.save(certificationEntity);
    }

    public CertificationEntity resetCertification(EmailCertificationRequestDto emailCertificationRequestDto) {
        String username = emailCertificationRequestDto.getUsername();
        String email = emailCertificationRequestDto.getEmail();

        String certificationNumber = CertificationNumber.getCertificationNumber();
        emailProvider.sendCertificationMail(email, certificationNumber);

        CertificationEntity certificationEntity = new CertificationEntity(username, email, bCryptPasswordEncoder.encode(certificationNumber));
        return certificationRepository.save(certificationEntity);
    }

    public void checkCertificationNumber(CheckCertificationRequestDto checkCertificationRequestDto) {
        String username  = checkCertificationRequestDto.getUsername();
        String email = checkCertificationRequestDto.getEmail();
        String certificationNumber = checkCertificationRequestDto.getCertificationNumber();

        CertificationEntity certificationEntity = certificationRepository.findByUsername(username);

        if(certificationEntity == null) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

        String encodedCertificationNumber = certificationEntity.getCertificationNumber();

        boolean isMatched = certificationEntity.getEmail().equals(email) && bCryptPasswordEncoder.matches(certificationNumber, encodedCertificationNumber);

        if(!isMatched) {
            throw new CustomException(ErrorCode.CERTIFICATION_FAILED);
        }
    }
}
