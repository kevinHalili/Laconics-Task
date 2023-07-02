package com.task.schoolservis.dto.error;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationErrorResponse {
    private Object errors;
    private String message = "The given data is invalid";

    public ValidationErrorResponse(Object errors) {
        this.errors = errors;
    }
}
