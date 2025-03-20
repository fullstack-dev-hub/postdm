package com.postdm.backend.domain.auth.api;

import com.postdm.backend.domain.auth.application.AuthService;
import com.postdm.backend.domain.auth.dto.IdCheckRequestDto;
import com.postdm.backend.domain.auth.dto.SignInRequestDto;
import com.postdm.backend.domain.auth.dto.SignUpRequestDto;
import com.postdm.backend.domain.email.application.EmailService;
import com.postdm.backend.domain.email.dto.CheckCertificationRequestDto;
import com.postdm.backend.global.jwt.dto.TokenInfo;
import com.postdm.backend.global.template.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth API", description = "회원가입 및 로그인 API")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController { // 로그인 및 회원 가입 컨트롤러

    private final AuthService authService;

    private final EmailService emailService;

    public AuthController(AuthService authService, EmailService emailService) {
        this.authService = authService;
        this.emailService = emailService;
    }

    @Operation(summary = "아이디 중복 확인 컨트롤러", description = "아이디 중복 확인을 요청하는 컨트롤러 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @PostMapping("/id-check") // 아이디 중복 확인 요청
    public ResponseTemplate<?> idCheck(@RequestBody @Valid IdCheckRequestDto idCheckRequestDto) {
        authService.idCheck(idCheckRequestDto.getUsername());
        return new ResponseTemplate<>(HttpStatus.OK, "사용할 수 있는 아이디 입니다.");
    }

    @Operation(summary = "이메일 인증 확인 컨트롤러", description = "이메일 인증 번호 확인을 요청하는 컨트롤러 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @PostMapping("/check-certification")
    public ResponseTemplate<?> checkCertificationNumber(@RequestBody @Valid CheckCertificationRequestDto checkCertificationRequestDto) {
        emailService.checkCertificationNumber(checkCertificationRequestDto);

        return new ResponseTemplate<>(HttpStatus.OK, "이메일 인증 성공");
    }

    @Operation(summary = "회원가입 컨트롤러", description = "회원가입을 요청하는 컨트롤러 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @PostMapping("/sign-up") // 회원 가입 요청
    public ResponseTemplate<?> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        authService.signUp(signUpRequestDto);

        return new ResponseTemplate<>(HttpStatus.OK, "회원가입 성공");
    }

    @Operation(summary = "로그인 컨트롤러", description = "로그인을 요청하는 컨트롤러 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @PostMapping("/sign-in") // 로그인 요청
    public ResponseTemplate<TokenInfo> signIn(@RequestBody @Valid SignInRequestDto signInRequestDto, HttpServletResponse response) {
        TokenInfo token = authService.signIn(signInRequestDto, response);

        return new ResponseTemplate<>(HttpStatus.OK, "로그인 성공", token);
    }

}
