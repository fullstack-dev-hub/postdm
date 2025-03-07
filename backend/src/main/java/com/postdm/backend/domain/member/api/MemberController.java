package com.postdm.backend.domain.member.api;

import com.postdm.backend.domain.email.dto.CheckCertificationRequestDto;
import com.postdm.backend.domain.member.application.MemberService;
import com.postdm.backend.domain.member.domain.entity.Member;
import com.postdm.backend.domain.member.dto.FindUsernameRequestDto;
import com.postdm.backend.domain.member.dto.ResetPasswordRequestDto;
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
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "아이디 찾기 컨트롤러")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @PostMapping("/find-userId")
    public ResponseTemplate<String> findUsername(@RequestBody @Valid FindUsernameRequestDto findUsernameRequestDto) {
        Member member = memberService.findUsernameByEmail(findUsernameRequestDto);

        String username = member.getUsername();

        String blurred = member.makeBlur(username);

        return new ResponseTemplate<>(HttpStatus.OK, "아이디 찾기 성공", blurred);
    }

    @Operation(summary = "비밀번호 재설정 이메일 인증 확인 컨트롤러")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @PostMapping("/check-certification")
    public ResponseTemplate<String> checkCertificationNumber(@RequestBody @Valid CheckCertificationRequestDto checkCertificationRequestDto) {
        String success =  memberService.checkCertificationNumber(checkCertificationRequestDto);

        return new ResponseTemplate<>(HttpStatus.OK, success, "****");
    }

    @Operation(summary = "비밀번호 재설정 컨트롤러")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @PostMapping("/reset-password")
    public ResponseTemplate<String> resetPassword(@RequestBody @Valid ResetPasswordRequestDto requestDto) {
        String success = memberService.resetPassword(requestDto);

        return new ResponseTemplate<>(HttpStatus.OK, success, "****");
    }
}
