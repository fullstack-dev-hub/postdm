package com.postdm.backend.domain.estimate.repository;

import com.postdm.backend.domain.estimate.entity.Estimate;
import com.postdm.backend.domain.estimate.entity.EstimateRepository;
import com.postdm.backend.domain.member.domain.entity.Member;
import com.postdm.backend.domain.member.domain.entity.MemberRole;
import com.postdm.backend.domain.member.domain.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
class EstimateRepositoryTest {

    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestEntityManager em;

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
    }

    @Test
    @DisplayName("견적서를 저장하고 조회할 수 있다")
    void 견적서를_저장하고_조회() {
        // when
        Estimate foundEstimate = estimateRepository.findById(testEstimate.getId()).orElse(null);

        // then
        assertThat(foundEstimate).isNotNull();
        assertThat(foundEstimate.getContent()).isEqualTo(testEstimate.getContent());
        assertThat(foundEstimate.getMember().getId()).isEqualTo(testMember.getId());
    }

    @Test
    @DisplayName("견적서를 조회하면 페이지네이션이 적용된다")
    void 견적서_조회_페이지네이션() {
        // given
        for (int i = 0; i < 5; i++) {
            Estimate estimate = new Estimate("content" + i, testMember);
            em.persist(estimate);
        }

        Pageable pageable = PageRequest.of(0, 3);

        // when
        Page<Estimate> page = estimateRepository.findByMember(testMember, pageable);

        // then
        assertThat(page.getContent()).hasSize(3);
        assertThat(page.getTotalElements()).isEqualTo(6);
    }

    @Test
    @DisplayName("존재하지 않는 견적서를 조회하면 빈 값이 반환된다")
    void 존재하지_않는_견적서를_조회하면_빈값이_반환() {
        // when
        Optional<Estimate> estimate = estimateRepository.findById(999L); // 없는 ID 조회

        // then
        assertThat(estimate).isEmpty();
    }

    @Test
    @DisplayName("견적서를 삭제할 수 있다")
    void 견적서를_삭제() {
        // when
        estimateRepository.delete(testEstimate);
        Optional<Estimate> deletedEstimate = estimateRepository.findById(testEstimate.getId());

        // then
        assertThat(deletedEstimate).isEmpty();
    }
}