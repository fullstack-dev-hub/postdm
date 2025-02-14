package com.postdm.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private EmailProvider emailProvider;

    public CertificationEntity emailCertification(EmailCertificationRequestDto emailCertificationRequestDto) {

        String username = emailCertificationRequestDto.getUsername();
        String email = emailCertificationRequestDto.getEmail();

        String certificationNumber = CertificationNumber.getCertificationNumber();

        emailProvider.sendCertificationMail(email, certificationNumber);

        CertificationEntity certificationEntity = new CertificationEntity(username, email, bCryptPasswordEncoder.encode(certificationNumber));

        return certificationRepository.save(certificationEntity);
    }

    public Member signUp(SignUpRequestDto signUpRequestDto) {
        String username = signUpRequestDto.getUsername();

        String email = signUpRequestDto.getEmail();
        String certificationNumber = signUpRequestDto.getCertificationNumber();

        CertificationEntity certificationEntity = certificationRepository.findByUsername(username);
        boolean isMatched = certificationEntity.getEmail().equals(email) && bCryptPasswordEncoder.matches(certificationNumber, certificationEntity.getCertificationNumber());

        if(!isMatched) {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }

        String password = signUpRequestDto.getPassword();
        String confirmPassword = signUpRequestDto.getConfirmPassword();

        if(!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(password);
        signUpRequestDto.setPassword(encodedPassword);

        Member member = Member.builder()
                .username(username)
                .email(email)
                .password(encodedPassword)
                .role(MemberRole.MEMBER).build();

        memberRepository.save(member);

        certificationRepository.delete(certificationEntity);

        return member;
    }

}
