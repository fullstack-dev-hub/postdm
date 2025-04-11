package com.postdm.backend.domain.estimate.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postdm.backend.domain.auth.dto.SignInRequestDto;
import com.postdm.backend.domain.auth.repository.TokenBlacklistRepository;
import com.postdm.backend.domain.member.domain.entity.Member;
import com.postdm.backend.domain.member.domain.entity.MemberRole;
import com.postdm.backend.domain.member.domain.repository.MemberRepository;
import com.postdm.backend.global.jwt.dto.TokenInfo;
import com.postdm.backend.global.jwt.util.JwtProvider;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class LogoutIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private String accessToken;


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
        accessToken = tokenInfo.getAccessToken();
    }

    @Test
    void 로그아웃_후_보호된_API_접근시_차단되어야_한다() throws Exception {
        // 로그아웃 요청
        mockMvc.perform(post("/api/v1/auth/sign-out")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        // 블랙리스트에 토큰 확인
        boolean exists = tokenBlacklistRepository.existsByToken(accessToken);
        assertTrue(exists, "토큰이 블랙리스트에 있어야 합니다.");

        // 보호된 API 호출 실패
        mockMvc.perform(get("/api/v1/estimates")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("이미 로그아웃된 토큰입니다."));
    }
}

