package com.postdm.backend;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private SecretKey secretKey;

    private final long expiredMs;

    private final long refreshedMs;

    public JwtProvider(@Value("${jwt.secret}")String secret, @Value("${jwt.expiredMS}") long expiredMs, @Value("${jwt.refreshedMs}") long refreshedMs) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.expiredMs = expiredMs;
        this.refreshedMs = refreshedMs;
    }

    public String generateAccessToken(String username) {
        Date now = new Date();
        Date accessTokenExpiredAt = new Date(now.getTime() + expiredMs);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(accessTokenExpiredAt)
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(String username) {
        Date now = new Date();
        Date refreshTokenExpiredAt = new Date(now.getTime() + refreshedMs);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(refreshTokenExpiredAt)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
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
            throw new JwtException("값이 존재하지 않는 토큰 입니다.");
        }

        return false;
    }

    public TokenInfo generateToken(String username) {

        String accessToken = generateAccessToken(username);
        String refreshToken = generateRefreshToken(username);

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();

        String username = claims.getSubject();

        return new UsernamePasswordAuthenticationToken(username, "", Collections.emptyList());
    }
}