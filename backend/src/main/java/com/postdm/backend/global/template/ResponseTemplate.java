package com.postdm.backend.global.template;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseTemplate<T> { // api 응답을 위한 템플릿
    int status;
    String message;
    T data;

    public ResponseTemplate(HttpStatus status, String message, @Nullable T data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

    public ResponseTemplate(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }
}
