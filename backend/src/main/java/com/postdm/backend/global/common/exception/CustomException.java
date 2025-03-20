package com.postdm.backend.global.common.exception;

import com.postdm.backend.global.common.response.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode code) {
        super(code.getMessage());
        this.errorCode = code;
    }

}
