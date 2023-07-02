package com.task.schoolservis.exception;


import com.task.schoolservis.dto.error.ErrorResponse;
import com.task.schoolservis.dto.error.ValidationErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundEx(NotFoundException ex, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse( ex.getMessage(), LocalDateTime.now(), webRequest.getDescription( false ) );

        return new ResponseEntity<>( errorResponse, HttpStatus.NOT_FOUND );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> forbiddenEx(ForbiddenException ex, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse( ex.getMessage(), LocalDateTime.now(), webRequest.getDescription( false ) );

        return new ResponseEntity<>( errorResponse, HttpStatus.FORBIDDEN );
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleEx(AppException ex, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse( ex.getMessage(), LocalDateTime.now(), webRequest.getDescription( false ) );

        return new ResponseEntity<>( errorResponse, ex.getStatus() );
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException validationException) {

        return new ResponseEntity<>( new ValidationErrorResponse( validationException.getErrors() ), HttpStatus.UNPROCESSABLE_ENTITY );
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return new ResponseEntity<>( this.getValidationResponse( ex ), HttpStatus.UNPROCESSABLE_ENTITY );
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return new ResponseEntity<>( this.getValidationResponse( ex ), HttpStatus.UNPROCESSABLE_ENTITY );
    }

    private ValidationErrorResponse getValidationResponse(BindException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach( (error) -> {
            FieldError e = ((FieldError) error);
            String fieldName = e.getField();
            String fieldMessage;
            try {
                fieldMessage = e.getDefaultMessage();
            } catch (Exception exception) {
                fieldMessage = e.getDefaultMessage();
            }
            errors.put( fieldName, fieldMessage );
        } );

        return new ValidationErrorResponse( errors );
    }
}
