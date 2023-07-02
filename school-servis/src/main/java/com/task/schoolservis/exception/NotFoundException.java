package com.task.schoolservis.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private String entity;
    private String field;
    private String value;

    public NotFoundException(String entity, String field, String value) {
        super( String.format( "%s not found with %s : '%s'", entity, field, value ) );
        this.entity = entity;
        this.value = value;
        this.field = field;
    }
}
