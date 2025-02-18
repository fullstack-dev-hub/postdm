package com.postdm.backend.global.config;

import com.postdm.backend.global.jwt.filter.JwtAuthenticationFilter;
import com.postdm.backend.global.jwt.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig { // 시큐리티 설정을 위한 Config

    @Autowired
    private JwtProvider jwtProvider;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable); // csrf 비활성화

        http
                .httpBasic(AbstractHttpConfigurer::disable); // httpBasic 비활성화

        http
                .formLogin(AbstractHttpConfigurer::disable); // formLogin 비활성화

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll() // /api/v1/auth로 시작하는 경로는 모두 허용(회원가입, 로그인 등)
                        .anyRequest().authenticated() // 이외의 경로는 인증을 필요로함.
                );

        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션 비활성화

        http
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class); // JWT 토큰 필터


        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity -> webSecurity.ignoring()
                .requestMatchers("/swagger-ui/**", "/favicon.ico", "/api-docs/**", "/v3/api-docs/**");
    }

}
