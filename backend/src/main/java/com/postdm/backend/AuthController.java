package com.postdm.backend;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/email-certification")
    public ResponseTemplate<CertificationEntity> emailCertification(@RequestBody @Valid EmailCertificationRequestDto emailCertificationRequestDto) {
        CertificationEntity certificationEntity = authService.emailCertification(emailCertificationRequestDto);

        return new ResponseTemplate<>(HttpStatus.OK, "이메일이 성공적으로 발송되었습니다.", certificationEntity);
    }

    @PostMapping("/sign-up")
    public ResponseTemplate<Member> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        Member member = authService.signUp(signUpRequestDto);

        return new ResponseTemplate<>(HttpStatus.OK, "회원가입 성공", member);
    }
}
