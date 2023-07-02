package com.task.schoolservis.exception;

import lombok.Getter;

@Getter

public class ForbiddenException extends RuntimeException {
    private String message;

    public ForbiddenException(String message) {
        super( message );
        this.message = message;
    }

}
