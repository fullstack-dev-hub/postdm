package com.postdm.backend.global.jwt.util;

import com.postdm.backend.domain.member.domain.entity.Member;
import com.postdm.backend.domain.member.domain.repository.MemberRepository;
import com.postdm.backend.domain.member.dto.MemberPrincipalDto;
import com.postdm.backend.global.common.exception.CustomException;
import com.postdm.backend.global.common.response.ErrorCode;
import com.postdm.backend.global.jwt.dto.TokenInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtProvider { // JWT 발급을 위한 Provider

    private SecretKey secretKey;

    private final long expiredMs;

    private final long refreshedMs;

    private final MemberRepository memberRepository;

    public JwtProvider(@Value("${jwt.secret}")String secret, @Value("${jwt.expiredMS}") long expiredMs, @Value("${jwt.refreshedMs}") long refreshedMs, MemberRepository memberRepository) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.expiredMs = expiredMs;
        this.refreshedMs = refreshedMs;
        this.memberRepository = memberRepository;
    }

    public String generateAccessToken(String username, String role) { // AccessToken 생성
        Date now = new Date();
        Date accessTokenExpiredAt = new Date(now.getTime() + expiredMs);

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(now)
                .expiration(accessTokenExpiredAt)
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(String username, String role) { // RefreshToken 생성
        Date now = new Date();
        Date refreshTokenExpiredAt = new Date(now.getTime() + refreshedMs);

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(now)
                .expiration(refreshTokenExpiredAt)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) { // 토큰 유효성 검사
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
            return true;
        }
        catch (SecurityException | MalformedJwtException e) {
            log.error("Signature verification failed");
            throw new JwtException("잘못된 JWT 서명 입니다.");
        }
        catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token");
            throw new JwtException("지원하지 않는 토큰 입니다.");
        }
        catch (ExpiredJwtException e) {
            log.error("Expired JWT token");
            throw new JwtException("만료된 토큰 입니다.");
        }
        catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty");
        }

        return false;
    }

    public TokenInfo generateToken(String username, String role) { // 토큰 생성

        String accessToken = generateAccessToken(username, role);

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .role(role)
                .build();
    }

    public Authentication getAuthentication(String token) { // 토큰에서 사용자 추출
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();

        String username = claims.getSubject();

        String role = claims.get("role", String.class);  // role 추출

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        MemberPrincipalDto memberPrincipalDto = MemberPrincipalDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .username(username)
                .build();

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));

        // Spring Security에서 Authentication 객체의 principal을 Member 객체로 저장
        return new UsernamePasswordAuthenticationToken(memberPrincipalDto, "", authorities);
    }

    // JWT 토큰 만료 시간 반환
    public LocalDateTime getExpiration(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Date expiration = claims.getExpiration();
        return expiration.toInstant()
                .atZone(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime();
    }
}