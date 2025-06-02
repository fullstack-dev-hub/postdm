package com.postdm.backend.domain.estimate.controller;

import com.postdm.backend.domain.estimate.dto.EstimateRequestDto;
import com.postdm.backend.domain.estimate.dto.EstimateResponseDto;
import com.postdm.backend.domain.estimate.service.EstimateService;
import com.postdm.backend.domain.member.dto.MemberPrincipalDto;
import com.postdm.backend.global.template.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Estimate API", description = "견적서 관련 API")
@RestController
@RequestMapping("/api/v1/estimates")
public class EstimateController {

    private final EstimateService estimateService;

    public EstimateController(EstimateService estimateService) {
        this.estimateService = estimateService;
    }

    @Operation(summary = "견적서 생성", description = "새로운 견적서를 생성합니다. 사용자는 content만 입력하며, title은 자동 생성됩니다.")
    @PostMapping
    public ResponseEntity<ResponseTemplate<EstimateResponseDto>> createEstimate(@AuthenticationPrincipal MemberPrincipalDto currentUser,
                                                                @RequestBody EstimateRequestDto requestDto) {

        EstimateResponseDto response = estimateService.createEstimate(requestDto.getContent(), currentUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseTemplate<>(HttpStatus.CREATED, "견적서 생성 성공", response));
    }

    @Operation(summary = "견적서 조회", description = "관리자는 모든 견적서를, 사용자는 본인의 견적서만 조회합니다.")
    @GetMapping
    public ResponseEntity<ResponseTemplate<List<EstimateResponseDto>>> getEstimates(@AuthenticationPrincipal MemberPrincipalDto currentUser) {
        List<EstimateResponseDto> response = estimateService.getEstimates(currentUser);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseTemplate<>(HttpStatus.OK, "견적서 조회 성공", response));
    }

    @Operation(summary = "견적서 상세 조회", description = "사용자의 특정 견적서를 상세 조회합니다.")
    @GetMapping("/{estimateId}")
    public ResponseEntity<ResponseTemplate<EstimateResponseDto>> getEstimateDetail(@PathVariable Long estimateId,
                                                                   @AuthenticationPrincipal MemberPrincipalDto principal) {
        EstimateResponseDto response = estimateService.getEstimateDetail(estimateId, principal);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseTemplate<>(HttpStatus.OK, "견적서 상세 조회 성공", response));
    }
}