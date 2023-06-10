package com.todomanager.api.common.exception.handler;

import com.todomanager.api.common.definition.ErrorCode;
import com.todomanager.api.common.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String[] validationErrors = new String[bindingResult.getFieldErrors().size()];

        bindingResult.getFieldErrors().forEach(fieldError -> {
            validationErrors[bindingResult.getFieldErrors().indexOf(fieldError)] = fieldError.getDefaultMessage();
        });

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(ErrorCode.VALIDATION_FAILURE)
                .timestamp(LocalDateTime.now())
                .messages(validationErrors)
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
}