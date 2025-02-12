package com.postdm.backend;

import org.springframework.http.HttpStatus;

public class RestTemplate<T> {
    int status;
    String message;
    T data;

    public RestTemplate(HttpStatus status, String message, T data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

}
