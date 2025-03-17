package com.postdm.backend.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@Tag(name = "Ping Check", description = "단순 서버 연결 상태 확인 API")
@RequestMapping("/api/v1")
public class PingController {

    @Operation(summary = "서버 상태 체크", description = "서버가 정상적으로 동작하는지 확인하는 API입니다.")
    @ApiResponse(responseCode = "200", description = "서버 정상 작동. 응답: text/plain (pong)")
    @GetMapping("/ping")
    public ResponseEntity<Map<String, String>> ping() {
        return ResponseEntity.ok(Collections.singletonMap("message", "pong"));
    }
}