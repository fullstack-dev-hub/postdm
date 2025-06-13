package com.postdm.backend.domain.estimate.dto;

import com.postdm.backend.domain.estimate.entity.Estimate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class EstimateListResponseDto {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private Long memberId;

    public static EstimateListResponseDto from(Estimate estimate) {
        return new EstimateListResponseDto(
                estimate.getId(),
                estimate.getTitle(),
                estimate.getCreatedAt(),
                estimate.getMember().getId()
        );
    }
}