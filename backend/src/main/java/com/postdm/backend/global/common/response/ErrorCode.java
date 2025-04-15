package com.postdm.backend.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //사용자 관련
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    AUTHORIZED_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "인증된 사용자를 찾을 수 없습니다."),

    // 로그인 및 회원가입 관련
    DUPLICATED_ID(HttpStatus.CONFLICT, "이미 사용중인 아이디 입니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일 입니다."),
    NOT_MATCHED_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
    SIGN_IN_FAILED(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 잘못되었습니다."),

    // 로그아웃 관련
    ALREADY_SIGN_OUT(HttpStatus.UNAUTHORIZED, "이미 로그아웃된 토큰입니다."),

    // 아이디 찾기 및 비밀번호 재설정 관련
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "존재하지 않는 이메일 입니다."),

    // 이메일 관련
    EMAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송 실패"),
    CERTIFICATION_FAILED(HttpStatus.BAD_REQUEST, "인증번호가 일치하지 않습니다."),

    // 견적서 관련
    ESTIMATE_NULL(HttpStatus.BAD_REQUEST, "견적서 내용은 비어있을 수 없습니다."),
    ESTIMATE_NOT_FOUND(HttpStatus.NOT_FOUND, "견적서를 찾을 수 없습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 견적서를 조회할 권한이 없습니다."),

    // 서버 에러
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러"),

    // 유효성 에러
    VALIDATION_FAIL(HttpStatus.BAD_REQUEST, "값이 비어있거나, 잘못된 입력 형식입니다."),


    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 HTTP 메서드를 호출했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러 입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}