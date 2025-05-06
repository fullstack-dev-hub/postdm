package com.postdm.backend.domain.estimate.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postdm.backend.domain.auth.domain.RefreshToken;
import com.postdm.backend.domain.auth.repository.RefreshTokenRepository;
import com.postdm.backend.domain.member.domain.entity.Member;
import com.postdm.backend.domain.member.domain.entity.MemberRole;
import com.postdm.backend.domain.member.domain.repository.MemberRepository;
import com.postdm.backend.global.jwt.dto.TokenInfo;
import com.postdm.backend.global.jwt.util.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ReissueIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        String rawPassword = "testpassword1!";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // 테스트용 사용자 등록
        Member member = Member.builder()
                .username("홍길동")
                .password(encodedPassword)
                .email("test@test.com")
                .role(MemberRole.MEMBER)
                .build();

        memberRepository.save(member);

        TokenInfo tokenInfo = jwtProvider.generateToken("홍길동", "MEMBER");
    }

    @Test
    void refreshToken을_이용하여_AccessToken을_재발급_받을_수_있다() throws Exception {
        // Refresh Token 생성 및 저장
        String refreshToken = jwtProvider.generateRefreshToken("홍길동", "MEMBER");
        LocalDateTime expiration = jwtProvider.getExpiration(refreshToken);
        refreshTokenRepository.save(new RefreshToken("홍길동", refreshToken, expiration));

        // /api/auth/reissue 요청 (쿠키에 Refresh Token 포함)
        MvcResult result = mockMvc.perform(post("/api/v1/auth/reissue")
                        .cookie(new Cookie("Refresh", refreshToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.role").value("MEMBER"))
                .andReturn();

        // 응답에서 새로운 Access Token 확인
        String responseBody = result.getResponse().getContentAsString();
        JsonNode json = objectMapper.readTree(responseBody);
        String newAccessToken = json.get("data").get("accessToken").asText();

        assertNotNull(newAccessToken);
    }

    @Test
    void 유효하지_않은_형식의_RefreshToken_요청_시_401에러() throws Exception {
        mockMvc.perform(post("/api/v1/auth/reissue")
                        .cookie(new Cookie("Refresh", "invalid-token")))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("유효하지 않은 Refresh Token입니다."));
    }
}