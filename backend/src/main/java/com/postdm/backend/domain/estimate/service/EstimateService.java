package com.postdm.backend.domain.estimate.service;

import com.postdm.backend.domain.estimate.dto.EstimateResponseDto;
import com.postdm.backend.domain.estimate.entity.Estimate;
import com.postdm.backend.domain.estimate.entity.EstimateRepository;
import com.postdm.backend.domain.estimate.service.policy.AdminEstimatePolicy;
import com.postdm.backend.domain.estimate.service.policy.UserEstimatePolicy;
import com.postdm.backend.domain.member.domain.entity.Member;
import com.postdm.backend.domain.member.domain.entity.MemberRole;
import com.postdm.backend.domain.member.domain.repository.MemberRepository;
import com.postdm.backend.domain.member.dto.MemberPrincipalDto;
import com.postdm.backend.global.common.exception.CustomException;
import com.postdm.backend.global.common.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstimateService {
    private final EstimateRepository estimateRepository;
    private final MemberRepository memberRepository;
    private final AdminEstimatePolicy adminPolicy;
    private final UserEstimatePolicy userPolicy;

    public EstimateResponseDto createEstimate(String content, MemberPrincipalDto principal) {
        if (content == null || content.trim().isEmpty()) {
            throw new CustomException(ErrorCode.ESTIMATE_NULL);
        }

        Member member = findMemberOrThrow(principal.getId());

        Estimate estimate = new Estimate(content, member);
        Estimate saved = estimateRepository.save(estimate);

        return new EstimateResponseDto(
            saved.getId(),
            saved.getTitle(),
            saved.getContent(),
            saved.getCreatedAt(),
            saved.getMember().getId()
        );
    }

    public List<EstimateResponseDto> getEstimates(MemberPrincipalDto principal) {
        Member member = findMemberOrThrow(principal.getId());

        List<Estimate> estimates = (member.getRole() == MemberRole.ADMIN)
                ? adminPolicy.getEstimates(member, estimateRepository)
                : userPolicy.getEstimates(member, estimateRepository);

        return estimates.stream()
                .map(e -> new EstimateResponseDto(
                        e.getId(), e.getTitle(), e.getContent(), e.getCreatedAt(), e.getMember().getId()
                ))
                .collect(Collectors.toList());
    }

    public EstimateResponseDto getEstimateDetail(Long estimateId, MemberPrincipalDto principal) {
        Member member = findMemberOrThrow(principal.getId());

        Estimate estimate = estimateRepository.findById(estimateId)
                .orElseThrow(() -> new CustomException(ErrorCode.ESTIMATE_NOT_FOUND));

        if (member.getRole() != MemberRole.ADMIN
                && estimate.getMember().getId() != member.getId()) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        return new EstimateResponseDto(
            estimate.getId(),
            estimate.getTitle(),
            estimate.getContent(),
            estimate.getCreatedAt(),
            estimate.getMember().getId()
        );
    }

    private Member findMemberOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.AUTHORIZED_MEMBER_NOT_FOUND));
    }
}