package com.postdm.backend.domain.estimate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postdm.backend.domain.estimate.dto.EstimateRequestDto;
import com.postdm.backend.domain.estimate.dto.EstimateResponseDto;
import com.postdm.backend.domain.estimate.entity.Estimate;
import com.postdm.backend.domain.estimate.entity.EstimateRepository;
import com.postdm.backend.domain.estimate.service.EstimateService;
import com.postdm.backend.domain.member.domain.entity.Member;
import com.postdm.backend.domain.member.domain.entity.MemberRole;
import com.postdm.backend.domain.member.domain.repository.MemberRepository;
import com.postdm.backend.domain.member.dto.MemberPrincipalDto;
import com.postdm.backend.domain.member.dto.TestPrincipalFactory;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class EstimateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    private EstimateService estimateService;

    private Member testMember;
    private Estimate testEstimate;
    private Authentication authentication;
    private MemberPrincipalDto principal;
    private EstimateResponseDto dto;
    private Pageable pageable;
    private Page<EstimateResponseDto> page;

    @BeforeEach
    void setUp() {
        estimateRepository.deleteAll();
        memberRepository.deleteAll();

        testMember = new Member(0L, "testUser", "nickname", "pass", "email@example.com", "01012345678", MemberRole.MEMBER);
        testMember = memberRepository.saveAndFlush(testMember);

        testEstimate = new Estimate("테스트 견적 내용입니다.", testMember);
        estimateRepository.saveAndFlush(testEstimate);

        principal = TestPrincipalFactory.create(testMember);

        authentication = new UsernamePasswordAuthenticationToken(
                principal, null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"))
        );
        dto = new EstimateResponseDto(1L, "title", "content", LocalDateTime.now(), 1L);
        pageable = PageRequest.of(0, 1);
        page = new PageImpl<>(List.of(dto), pageable, 1);
    }

    @Test
    @DisplayName("견적서 생성 API 테스트")
    void 견적서_생성_API_테스트() throws Exception {
        String requestJson = objectMapper.writeValueAsString(
                new EstimateRequestDto("테스트 견적서 내용입니다.")
        );

        mockMvc.perform(post("/api/v1/estimates")
                        .with(SecurityMockMvcRequestPostProcessors.authentication(authentication))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.content").value("테스트 견적서 내용입니다."));
    }

    @Test
    @DisplayName("견적서 조회 API 테스트")
    void 견적서_첫페이지_조회_API_테스트() throws Exception {
        mockMvc.perform(get("/api/v1/estimates")
                        .param("page", "0")
                        .param("size", "1")
                        .with(SecurityMockMvcRequestPostProcessors.authentication(authentication)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content.length()").value(1));
    }
}