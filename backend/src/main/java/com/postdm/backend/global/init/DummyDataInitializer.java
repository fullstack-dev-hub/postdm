package com.postdm.backend.global.init;

import com.postdm.backend.domain.estimate.entity.Estimate;
import com.postdm.backend.domain.estimate.entity.EstimateRepository;
import com.postdm.backend.domain.member.domain.entity.Member;
import com.postdm.backend.domain.member.domain.repository.MemberRepository;
import com.postdm.backend.global.common.exception.CustomException;
import com.postdm.backend.global.common.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("dev") // 로컬에서만 실행되도록 제한
public class DummyDataInitializer implements CommandLineRunner {
    private final EstimateRepository estimateRepository;
    private final MemberRepository memberRepository;

    @Override
    public void run(String... args) {
        if (estimateRepository.count() > 0) {
            log.info("[INIT] 이미 견적서 데이터가 존재합니다. 초기화를 건너뜁니다.");
            return;
        }

        log.info("[INIT] 더미 견적서 생성 시작");

        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        for (int i = 0; i < 10000; i++) {
            Estimate estimate = Estimate.builder()
                    .content("더미 데이터 자동 생성 " + i)
                    .member(member)
                    .build();
            estimateRepository.save(estimate);
        }

        log.info("[INIT] 테스트용 견적서 10000건 삽입 완료");
    }
}