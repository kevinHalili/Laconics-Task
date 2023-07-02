package com.task.schoolservis.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public AppException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
