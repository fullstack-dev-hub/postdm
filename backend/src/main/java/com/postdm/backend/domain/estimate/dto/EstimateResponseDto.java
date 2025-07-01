package com.postdm.backend.domain.estimate.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.postdm.backend.domain.estimate.entity.Estimate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Schema(description = "견적서 응답 DTO")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EstimateResponseDto {

    @Schema(description = "견적서 ID", example = "1")
    private Long id;

    @Schema(description = "견적서 제목 (내용 앞 10글자 + **** 자동 생성)", example = "이것은 견적서의 내...")
    private String title;

    @Schema(description = "견적서 내용", example = "이것은 견적서의 내용입니다.")
    private String content;

    @Schema(description = "생성 날짜", example = "2024-02-18T12:34:56")
    private LocalDateTime createdAt;

    @Schema(description = "작성자", example = "user1")
    private Long memberId;

    public static EstimateResponseDto from(Estimate estimate) {
        return new EstimateResponseDto(
                estimate.getId(),
                estimate.getTitle(),
                estimate.getContent(),
                estimate.getCreatedAt(),
                estimate.getMember().getId()
        );
    }
}