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
public class AuthService { // 로그인 및 회원가입 서비스

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

    public String idCheck(String username) { // 아이디 중복확인 서비스
        boolean existedUsername = memberRepository.existsByUsername(username); // 데이터베이스에서 사용자 아이디가 존재하는지 여부
        if(existedUsername) {
            throw new IllegalArgumentException("이미 사용중인 아이디 입니다.");
        }
        return username;
    }

    public Member signUp(SignUpRequestDto signUpRequestDto) { // 회원가입 서비스
        String nickname = signUpRequestDto.getNickname();

        String username = signUpRequestDto.getUsername();
        boolean existedUsername = memberRepository.existsByUsername(username); // 데이터베이스에서 사용자 아이디가 존재하는지 여부

        if (existedUsername) {
            throw new IllegalArgumentException("이미 사용중인 아이디 입니다.");
        }

        String password = signUpRequestDto.getPassword();
        String confirmPassword = signUpRequestDto.getConfirmPassword();

        if(!password.equals(confirmPassword)) { // 입력한 비밀번호 일치 여부
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(password); // 비밀번호 암호화
        signUpRequestDto.setPassword(encodedPassword);

        String email = signUpRequestDto.getEmail();
        boolean existedEmail = memberRepository.existsByEmail(email); // 데이터베이스에서 사용자 이메일이 존재하는지 여부

        if (existedEmail) {
            throw new IllegalArgumentException("이미 사용중인 이메일 입니다.");
        }

        CertificationEntity certificationEntity = certificationRepository.findByUsername(username); // 데이터베이스에서 사용자 이름으로 된 인증 번호 조회

        String certificationNumber = signUpRequestDto.getCertificationNumber();
        String encodedCertificationNumber = certificationEntity.getCertificationNumber();

        // 데이터베이스 내에서 저장된 인증번호는 암호화되어 있으므로, 복호화 하여 인증번호가 일치하는지 확인
        boolean isMatched = certificationEntity.getEmail().equals(email) && bCryptPasswordEncoder.matches(certificationNumber, encodedCertificationNumber);

        if(!isMatched) {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }

        String phone = signUpRequestDto.getPhone();

        Member member = Member.builder() // 맴버 객체 생성
                .nickname(nickname)
                .username(username)
                .password(encodedPassword)
                .email(email)
                .phone(phone)
                .role(MemberRole.MEMBER).build(); // 기본적으로 role은 사용자로 저장

        memberRepository.save(member); // 데이터베이스에 멤버 저장

        certificationRepository.delete(certificationEntity); // 회원가입이 완료되면 데이터베이스에서 해당 인증번호 삭제

        return member;
    }

    public String signIn(SignInRequestDto signInRequestDto, HttpServletResponse response) { // 로그인 서비스
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

        String role = member.getRole().name();

        TokenInfo token = jwtProvider.generateToken(username, role); // 로그인이 완료되면 토큰 생성
        String refreshToken = token.getRefreshToken();

        response.addCookie(createCookie("Refresh", refreshToken)); // 쿠키에 refresh 토큰 담음

        return token.getAccessToken(); // 응답 body에는 access 토큰 반환
    }

    private Cookie createCookie(String name, String value) { // 쿠키 생성 메소드
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(refreshedMS);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        return cookie;
    }

}
