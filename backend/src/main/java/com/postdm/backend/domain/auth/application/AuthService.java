package com.postdm.backend.domain.auth.application;

import com.postdm.backend.domain.auth.dto.SignInRequestDto;
import com.postdm.backend.domain.auth.dto.SignUpRequestDto;
import com.postdm.backend.domain.email.domain.entity.CertificationEntity;
import com.postdm.backend.domain.email.domain.repository.CertificationRepository;
import com.postdm.backend.domain.member.domain.entity.Member;
import com.postdm.backend.domain.member.domain.entity.MemberRole;
import com.postdm.backend.domain.member.domain.repository.MemberRepository;
import com.postdm.backend.global.jwt.dto.TokenInfo;
import com.postdm.backend.global.jwt.util.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

    @Value("${jwt.expiredMS}")
    private int refreshedMS;

    public String idCheck(String username) {
        boolean existedUsername = memberRepository.existsByUsername(username);
        if(existedUsername) {
            throw new IllegalArgumentException("이미 사용중인 아이디 입니다.");
        }
        return username;
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

    public String signIn(SignInRequestDto signInRequestDto, HttpServletResponse response) {
        String username = signInRequestDto.getUsername();

        Member member = memberRepository.findByUsername(username);
        if(member == null) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        String password = signInRequestDto.getPassword();
        String encodedPassword = member.getPassword();

        boolean isMatched = bCryptPasswordEncoder.matches(password, encodedPassword);
        if(!isMatched) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        String refreshToken = jwtProvider.generateRefreshToken(username);

        response.addCookie(createCookie("Refresh", refreshToken));

        TokenInfo token = jwtProvider.generateToken(username);

        return token.getAccessToken();
    }

    private Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(refreshedMS);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        return cookie;
    }

}
