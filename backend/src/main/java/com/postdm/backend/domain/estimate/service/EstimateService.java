package com.postdm.backend.domain.estimate.service;

import com.postdm.backend.domain.estimate.dto.EstimateResponseDto;
import com.postdm.backend.domain.estimate.entity.Estimate;
import com.postdm.backend.domain.estimate.entity.EstimateRepository;
import com.postdm.backend.domain.estimate.service.policy.AdminEstimatePolicy;
import com.postdm.backend.domain.estimate.service.policy.UserEstimatePolicy;
import com.postdm.backend.domain.member.domain.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.postdm.backend.domain.member.domain.entity.MemberRole.ADMIN;

@Slf4j
@Service
public class EstimateService {
    private final EstimateRepository estimateRepository;
    private final AdminEstimatePolicy adminPolicy;
    private final UserEstimatePolicy userPolicy;

    public EstimateService(EstimateRepository estimateRepository,
                           AdminEstimatePolicy adminPolicy,
                           UserEstimatePolicy userPolicy) {
        this.estimateRepository = estimateRepository;
        this.adminPolicy = adminPolicy;
        this.userPolicy = userPolicy;
    }

    public EstimateResponseDto createEstimate(String content, Member member) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("견적서 내용은 비어 있을 수 없습니다.");
        }

        if (member == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof Member) {
                member = (Member) authentication.getPrincipal();
            } else {
                throw new RuntimeException("인증된 사용자가 존재하지 않습니다.");
            }
        }

        Estimate estimate = new Estimate(content, member);
        Estimate savedEstimate = estimateRepository.save(estimate);

        return new EstimateResponseDto(
            savedEstimate.getId(),
            savedEstimate.getTitle(),
            savedEstimate.getContent(),
            savedEstimate.getCreatedAt(),
            savedEstimate.getMember().getId()
        );
    }

    public List<EstimateResponseDto> getEstimates(Member member) {
        if (member == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof Member) {
                member = (Member) authentication.getPrincipal();
            } else {
                throw new RuntimeException("인증된 사용자가 존재하지 않습니다.");
            }
        }

        List<Estimate> estimates;

        if (member.getRole() == ADMIN) {
            estimates = adminPolicy.getEstimates(member, estimateRepository);
        } else {
            estimates = userPolicy.getEstimates(member, estimateRepository);
        }

        return estimates.stream()
                .map(estimate -> new EstimateResponseDto(
                        estimate.getId(),
                        estimate.getTitle(),
                        estimate.getContent(),
                        estimate.getCreatedAt(),
                        estimate.getMember().getId()
                ))
                .collect(Collectors.toList());
    }
}