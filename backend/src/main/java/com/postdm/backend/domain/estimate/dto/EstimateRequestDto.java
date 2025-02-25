package com.postdm.backend.domain.estimate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "견적서 생성 요청 DTO")
public class EstimateRequestDto {
    @Schema(description = "견적서 내용", example = "이것은 견적서의 내용입니다.")
    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String content;
}