package com.postdm.backend.domain.estimate.service;

import com.postdm.backend.domain.estimate.dto.EstimateRequestDto;
import com.postdm.backend.domain.estimate.dto.EstimateResponseDto;
import com.postdm.backend.domain.estimate.entity.Estimate;
import com.postdm.backend.domain.estimate.entity.EstimateRepository;
import com.postdm.backend.domain.member.domain.entity.Member;
import com.postdm.backend.domain.member.domain.entity.MemberRole;
import com.postdm.backend.domain.member.domain.repository.MemberRepository;
import com.postdm.backend.domain.member.dto.MemberPrincipalDto;
import com.postdm.backend.domain.member.dto.TestPrincipalFactory;
import com.postdm.backend.global.common.exception.CustomException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;


import org.springframework.data.domain.Pageable;
import java.util.Collections;


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
        // given
        EstimateRequestDto requestDto = new EstimateRequestDto("새로운 견적서 내용입니다.");
        MemberPrincipalDto principal = TestPrincipalFactory.create(testMember);

        // when
        EstimateResponseDto responseDto = estimateService.createEstimate(requestDto.getContent(), principal);

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getContent()).isEqualTo("새로운 견적서 내용입니다.");
        assertThat(responseDto.getMemberId()).isEqualTo(testMember.getId());
    }

    @Test
    @DisplayName("비어 있는 내용으로 견적서를 생성하면 예외가 발생해야 한다")
    void 비어있는_내용으로_견적서를_생성하면_예외가_발생() {
        EstimateRequestDto requestDto = new EstimateRequestDto("");
        MemberPrincipalDto principal = TestPrincipalFactory.create(testMember);

        assertThrows(RuntimeException.class, () -> {
            estimateService.createEstimate(requestDto.getContent(), principal);
        });
    }


    @Test
    @DisplayName("사용자가 본인의 견적서를 페이지네이션해서 조회할 수 있다")
    void 사용자는_자신의_견적서만_페이지네이션해서_조회() {
        // given
        MemberPrincipalDto principal = TestPrincipalFactory.create(testMember);
        Pageable pageable = PageRequest.of(0, 2);

        // when
        Page<EstimateResponseDto> result = estimateService.getEstimates(principal, pageable);

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("인증되지 않은 사용자가 견적서를 조회하면 예외가 발생해야 한다")
    void 인증되지_않은_사용자가_견적서를_조회하면_예외_발생() {
        // given
        SecurityContextHolder.clearContext();
        Pageable pageable = PageRequest.of(0, 2);

        // when
        Exception exception = assertThrows(Exception.class, () -> {
            estimateService.getEstimates(null, pageable);
        });

        // then
        System.out.println("예외 클래스: " + exception.getClass().getName());
        System.out.println("예외 메시지: " + exception.getMessage());
    }

    @Test
    @DisplayName("일반 사용자가 본인의 견적서를 상세 조회할 수 있다")
    void 일반_사용자는_자신의_견적서_상세_조회_가능() {
        // given
        MemberPrincipalDto principal = TestPrincipalFactory.create(testMember);

        // when
        EstimateResponseDto response = estimateService.getEstimateDetail(testEstimate.getId(), principal);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getContent()).isEqualTo(testEstimate.getContent());
        assertThat(response.getMemberId()).isEqualTo(testMember.getId());
    }

    @Test
    @DisplayName("일반 사용자가 다른 사람의 견적서를 상세 조회하면 예외가 발생한다")
    void 일반_사용자는_다른_사용자의_견적서_상세_조회_불가() {
        // given
        Member otherUser = new Member(0L, "otherUser", "nickname2", "pass", "other@ex.com", "01012345678", MemberRole.MEMBER);
        memberRepository.saveAndFlush(otherUser);

        MemberPrincipalDto otherPrincipal = TestPrincipalFactory.create(otherUser);

        // when & then
        assertThrows(CustomException.class, () -> {
            estimateService.getEstimateDetail(testEstimate.getId(), otherPrincipal);
        });
    }

    @Test
    @DisplayName("관리자는 모든 사용자의 견적서를 상세 조회할 수 있다")
    void 관리자는_모든_견적서_상세_조회_가능() {
        // given
        Member admin = new Member(0L, "admin", "관리자", "adminpass", "admin@ex.com", "01000000000", MemberRole.ADMIN);
        memberRepository.saveAndFlush(admin);
        MemberPrincipalDto principal = TestPrincipalFactory.create(testMember);

        // when
        EstimateResponseDto response = estimateService.getEstimateDetail(testEstimate.getId(), principal);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getContent()).isEqualTo(testEstimate.getContent());
        assertThat(response.getMemberId()).isEqualTo(testEstimate.getMember().getId());
    }
}