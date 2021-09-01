package com.test.tpirates.exception;

// HTTP Status code
// 2xx > OK
// 4xx > Client
// 5xx > Server

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GoodsNotFoundException extends RuntimeException {
    public GoodsNotFoundException(String message) {
        super(message);
    }
}
