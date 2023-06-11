package com.todomanager.api.task.exception.handler;

import com.todomanager.api.common.definition.ErrorCode;
import com.todomanager.api.common.response.ErrorResponse;
import com.todomanager.api.task.exception.TaskCategoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class TaskCategoryNotFoundExceptionHandler {

    @ExceptionHandler(TaskCategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException() {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(ErrorCode.TASK_CATEGORY_NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .messages(new String[]{"Kategori bulunamadı!"})
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}