package com.postdm.backend;

import org.springframework.http.HttpStatus;

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
