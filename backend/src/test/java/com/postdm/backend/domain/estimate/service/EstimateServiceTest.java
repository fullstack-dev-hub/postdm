package com.postdm.backend.domain.estimate.service;

import com.postdm.backend.domain.estimate.dto.EstimateRequestDto;
import com.postdm.backend.domain.estimate.dto.EstimateResponseDto;
import com.postdm.backend.domain.estimate.entity.Estimate;
import com.postdm.backend.domain.estimate.entity.EstimateRepository;
import com.postdm.backend.domain.member.domain.entity.Member;
import com.postdm.backend.domain.member.domain.entity.MemberRole;
import com.postdm.backend.domain.member.domain.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@ExtendWith(MockitoExtension.class)
class EstimateServiceTest {

    @Autowired
    private EstimateService estimateService;

    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    private MemberRepository memberRepository;

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
    @DisplayName("견적서를 생성하면 정상적으로 반환해야 한다")
    void 견적서를_생성하면_정상적으로_반환() {
        // Given
        EstimateRequestDto requestDto = new EstimateRequestDto("새로운 견적서 내용입니다.");

        // When
        EstimateResponseDto responseDto = estimateService.createEstimate(requestDto.getContent(), testMember);

        // Then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getContent()).isEqualTo("새로운 견적서 내용입니다.");
        assertThat(responseDto.getMemberId()).isEqualTo(testMember.getId());
    }

    @Test
    @DisplayName("비어 있는 내용으로 견적서를 생성하면 예외가 발생해야 한다")
    void 비어있는_내용으로_견적서를_생성하면_예외가_발생() {
        EstimateRequestDto requestDto = new EstimateRequestDto("");

        assertThrows(RuntimeException.class, () -> {
            estimateService.createEstimate(requestDto.getContent(), testMember);
        });
    }

    @Test
    @DisplayName("사용자가 본인의 견적서를 조회할 수 있다")
    void 사용자가_본인의_견적서를_조회() {
        // When
        List<EstimateResponseDto> estimates = estimateService.getEstimates(testMember);

        // Then
        assertThat(estimates).isNotEmpty();
        assertThat(estimates.get(0).getContent()).isEqualTo(testEstimate.getContent());
        assertThat(estimates.get(0).getMemberId()).isEqualTo(testMember.getId());
    }

    @Test
    @DisplayName("인증되지 않은 사용자가 견적서를 조회하면 예외가 발생해야 한다")
    void 인증되지_않은_사용자가_견적서를_조회하면_예외_발생() {
        SecurityContextHolder.clearContext();

        Exception exception = assertThrows(Exception.class, () -> {
            estimateService.getEstimates(null);
        });

        System.out.println("예외 클래스: " + exception.getClass().getName());
        System.out.println("예외 메시지: " + exception.getMessage());
    }
}