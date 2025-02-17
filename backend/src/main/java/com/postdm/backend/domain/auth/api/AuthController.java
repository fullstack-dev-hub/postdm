package com.postdm.backend.domain.auth.api;

import com.postdm.backend.domain.auth.application.AuthService;
import com.postdm.backend.domain.auth.dto.IdCheckRequestDto;
import com.postdm.backend.domain.auth.dto.SignInRequestDto;
import com.postdm.backend.domain.auth.dto.SignUpRequestDto;
import com.postdm.backend.domain.member.domain.entity.Member;
import com.postdm.backend.global.template.ResponseTemplate;
import jakarta.servlet.http.HttpServletResponse;
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

    @PostMapping("/id-check")
    public ResponseTemplate<String> idCheck(@RequestBody IdCheckRequestDto idCheckRequestDto) {
        String username = authService.idCheck(idCheckRequestDto.getUsername());
        return new ResponseTemplate<>(HttpStatus.OK, "사용할 수 있는 아이디 입니다.", username);
    }

    @PostMapping("/sign-up")
    public ResponseTemplate<Member> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        Member member = authService.signUp(signUpRequestDto);

        return new ResponseTemplate<>(HttpStatus.OK, "회원가입 성공", member);
    }

    @PostMapping("/sign-in")
    public ResponseTemplate<String> signIn(@RequestBody @Valid SignInRequestDto signInRequestDto, HttpServletResponse response) {
        String token = authService.signIn(signInRequestDto, response);

        return new ResponseTemplate<>(HttpStatus.OK, "로그인 성공", token);
    }
}
