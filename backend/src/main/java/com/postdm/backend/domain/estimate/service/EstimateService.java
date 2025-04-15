package com.postdm.backend.domain.estimate.service;

import com.postdm.backend.domain.estimate.dto.EstimateResponseDto;
import com.postdm.backend.domain.estimate.entity.Estimate;
import com.postdm.backend.domain.estimate.entity.EstimateRepository;
import com.postdm.backend.domain.estimate.service.policy.AdminEstimatePolicy;
import com.postdm.backend.domain.estimate.service.policy.UserEstimatePolicy;
import com.postdm.backend.domain.member.domain.entity.Member;
import com.postdm.backend.global.common.exception.CustomException;
import com.postdm.backend.global.common.response.ErrorCode;
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
            throw new CustomException(ErrorCode.ESTIMATE_NULL);
        }

        if (member == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof Member) {
                member = (Member) authentication.getPrincipal();
            } else {
                throw new CustomException(ErrorCode.AUTHORIZED_MEMBER_NOT_FOUND);
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
                throw new CustomException(ErrorCode.AUTHORIZED_MEMBER_NOT_FOUND);
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

    public EstimateResponseDto getEstimateDetail(Long estimateId, Member member) {
        if (member == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof Member) {
                member = (Member) authentication.getPrincipal();
            } else {
                throw new CustomException(ErrorCode.AUTHORIZED_MEMBER_NOT_FOUND);
            }
        }

        Estimate estimate = estimateRepository.findById(estimateId)
                .orElseThrow(() -> new CustomException(ErrorCode.ESTIMATE_NOT_FOUND));

        // 권한 체크
        if (member.getRole() != ADMIN &&
                estimate.getMember().getId() != member.getId()) {
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
}