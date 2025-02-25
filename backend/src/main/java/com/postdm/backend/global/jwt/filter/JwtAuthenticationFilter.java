package com.postdm.backend.global.jwt.filter;


import com.postdm.backend.global.jwt.util.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { // JWT 필터. 해당 필터를 통해 JWT가 유효한지 판단

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = resolveToken(request);

        if (token != null && jwtProvider.validateToken(token)) {
            Authentication auth = jwtProvider.getAuthentication(token); // 사용자 추출
            SecurityContextHolder.getContext().setAuthentication(auth); // SecurityContextHolder에 사용자 등록
        }
        else {
            log.warn("[JwtAuthenticationFilter] Invalid or missing token.");
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) { // 헤더에서 토큰 추출
        String bearerToken = request.getHeader("Authorization"); // 헤더의 Authorization에서 값을 가져옴.
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) { // Bearer를 제외한 토큰 값 가져오기
            return bearerToken.substring(7);
        }
        return null;
    }
}
