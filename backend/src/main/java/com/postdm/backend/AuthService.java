package com.postdm.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    @Value("${jwt.expiredMS}")
    private int refreshedMS;

    public String idCheck(String username) {
        boolean existedUsername = memberRepository.existsByUsername(username);
        if(existedUsername) {
            throw new IllegalArgumentException("이미 사용중인 아이디 입니다.");
        }
        return username;
    }

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

    public Member signUp(SignUpRequestDto signUpRequestDto) {
        String nickname = signUpRequestDto.getNickname();

        String username = signUpRequestDto.getUsername();
        boolean existedUsername = memberRepository.existsByUsername(username);

        if (existedUsername) {
            throw new IllegalArgumentException("이미 사용중인 아이디 입니다.");
        }

        String password = signUpRequestDto.getPassword();
        String confirmPassword = signUpRequestDto.getConfirmPassword();

        if(!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(password);
        signUpRequestDto.setPassword(encodedPassword);

        String email = signUpRequestDto.getEmail();
        boolean existedEmail = memberRepository.existsByEmail(email);

        if (existedEmail) {
            throw new IllegalArgumentException("이미 사용중인 이메일 입니다.");
        }

        String certificationNumber = signUpRequestDto.getCertificationNumber();

        CertificationEntity certificationEntity = certificationRepository.findByUsername(username);
        boolean isMatched = certificationEntity.getEmail().equals(email) && bCryptPasswordEncoder.matches(certificationNumber, certificationEntity.getCertificationNumber());

        if(!isMatched) {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }

        String phone = signUpRequestDto.getPhone();

        Member member = Member.builder()
                .nickname(nickname)
                .username(username)
                .password(encodedPassword)
                .email(email)
                .phone(phone)
                .role(MemberRole.MEMBER).build();

        memberRepository.save(member);

        certificationRepository.delete(certificationEntity);

        return member;
    }

}
