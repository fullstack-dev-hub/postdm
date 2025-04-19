package com.postdm.backend.domain.estimate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postdm.backend.domain.estimate.dto.EstimateRequestDto;
import com.postdm.backend.domain.estimate.entity.Estimate;
import com.postdm.backend.domain.estimate.entity.EstimateRepository;
import com.postdm.backend.domain.estimate.service.EstimateService;
import com.postdm.backend.domain.member.domain.entity.Member;
import com.postdm.backend.domain.member.domain.entity.MemberRole;
import com.postdm.backend.domain.member.domain.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@ExtendWith(MockitoExtension.class)
class EstimateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EstimateRepository estimateRepository;

    @Mock
    private EstimateService estimateService;

    @InjectMocks
    private EstimateController estimateController;

    private Member testMember;
    private Estimate testEstimate;

    @BeforeEach
    void setUp() {
        estimateRepository.deleteAll();
        memberRepository.deleteAll();

        testMember = new Member(0L, "testUser", "test1", "password", "test@example.com", "01011111111", MemberRole.MEMBER);
        testMember = memberRepository.saveAndFlush(testMember);

        testEstimate = new Estimate("테스트 견적 내용입니다.", testMember);
        estimateRepository.saveAndFlush(testEstimate);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                testMember, null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    @DisplayName("견적서 생성 API 테스트")
    void 견적서_생성_API_테스트() throws Exception {
        // Given
        String requestJson = objectMapper.writeValueAsString(new EstimateRequestDto("테스트 견적서 내용입니다."));

        // When & Then
        mockMvc.perform(post("/api/v1/estimates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").value("테스트 견적서 내용입니다."));
    }

    @Test
    @DisplayName("견적서 조회 API 테스트")
    void 견적서_조회_API_테스트() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/estimates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()").value(1))
                .andExpect(jsonPath("$.data[0].content").value("테스트 견적 내용입니다."));
    }

    @Test
    @DisplayName("인증되지 않은 사용자가 견적서를 조회하면 403 응답을 반환해야 한다")
    void 인증되지_않은_사용자가_견적서를_조회하면_403_반환() throws Exception {
        SecurityContextHolder.clearContext();

        mockMvc.perform(get("/api/v1/estimates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}