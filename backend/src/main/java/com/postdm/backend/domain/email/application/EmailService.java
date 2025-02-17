package com.postdm.backend.domain.email.application;

import com.postdm.backend.domain.email.domain.entity.CertificationEntity;
import com.postdm.backend.domain.email.domain.repository.CertificationRepository;
import com.postdm.backend.domain.email.dto.EmailCertificationRequestDto;
import com.postdm.backend.domain.member.domain.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailProvider emailProvider;

    public CertificationEntity emailCertification(EmailCertificationRequestDto emailCertificationRequestDto) {

        String username = emailCertificationRequestDto.getUsername();
        String email = emailCertificationRequestDto.getEmail();

        boolean existedEmail = memberRepository.existsByEmail(email);
        if (existedEmail) {
            throw new IllegalArgumentException("이미 사용중인 이메일 입니다.");
        }

        String certificationNumber = CertificationNumber.getCertificationNumber();

        emailProvider.sendCertificationMail(email, certificationNumber);

        CertificationEntity certificationEntity = new CertificationEntity(username, email, bCryptPasswordEncoder.encode(certificationNumber));

        return certificationRepository.save(certificationEntity);
    }
}
