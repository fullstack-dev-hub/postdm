package com.postdm.backend;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseTemplate<T> {
    int status;
    String message;
    T data;

    public ResponseTemplate(HttpStatus status, String message, T data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }
}
