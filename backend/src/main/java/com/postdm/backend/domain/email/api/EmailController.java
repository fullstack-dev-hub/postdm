package com.postdm.backend.domain.email.api;

import com.postdm.backend.domain.email.application.EmailService;
import com.postdm.backend.domain.email.domain.entity.CertificationEntity;
import com.postdm.backend.domain.email.dto.EmailCertificationRequestDto;
import com.postdm.backend.global.template.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController { // 이메일 관련 컨트롤러

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @Operation(summary = "회원가입용 이메일 전송 컨트롤러")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @PostMapping("/email-certification") // 이메일 전송 요청 api
    public ResponseTemplate<?> emailCertification(@RequestBody @Valid EmailCertificationRequestDto emailCertificationRequestDto) {
        emailService.emailCertification(emailCertificationRequestDto);

        return new ResponseTemplate<>(HttpStatus.OK, "이메일이 성공적으로 발송되었습니다.");
    }

    @Operation(summary = "비밀번호 재설정용 이메일 전송 컨트롤러")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @PostMapping("/reset-certification")
    public ResponseTemplate<?> resetCertification(@RequestBody @Valid EmailCertificationRequestDto emailCertificationRequestDto) {
        emailService.resetCertification(emailCertificationRequestDto);

        return new ResponseTemplate<>(HttpStatus.OK, "이메일이 성공적으로 발송되었습니다.");
    }
}
