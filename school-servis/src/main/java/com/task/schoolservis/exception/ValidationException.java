package com.task.schoolservis.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationException extends RuntimeException {
    Map<String, String> errors;

    public ValidationException(Map<String, String> errors) {
        this.errors = errors;
    }
}
