package com.postdm.backend.global.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String message;

    public  ErrorResponse(ErrorCode code) {
        this.status = code.getHttpStatus().value();
        this.message = code.getMessage();
    }
}
