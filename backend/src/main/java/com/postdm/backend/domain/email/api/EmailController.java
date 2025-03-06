package com.postdm.backend.domain.email.api;

import com.postdm.backend.domain.email.application.EmailService;
import com.postdm.backend.domain.email.domain.entity.CertificationEntity;
import com.postdm.backend.domain.email.dto.EmailCertificationRequestDto;
import com.postdm.backend.global.template.ResponseTemplate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController { // 이메일 관련 컨트롤러

    @Autowired
    private EmailService emailService;

    @PostMapping("/email-certification") // 이메일 전송 요청 api
    public ResponseTemplate<CertificationEntity> emailCertification(@RequestBody @Valid EmailCertificationRequestDto emailCertificationRequestDto) {
        CertificationEntity certificationEntity = emailService.emailCertification(emailCertificationRequestDto);

        return new ResponseTemplate<>(HttpStatus.OK, "이메일이 성공적으로 발송되었습니다.", certificationEntity);
    }

    @PostMapping("/reset-certification")
    public ResponseTemplate<CertificationEntity> resetCertification(@RequestBody @Valid EmailCertificationRequestDto emailCertificationRequestDto) {
        CertificationEntity certificationEntity = emailService.resetCertification(emailCertificationRequestDto);

        return new ResponseTemplate<>(HttpStatus.OK, "이메일이 성공적으로 발송되었습니다.", certificationEntity);
    }
}
