package com.todomanager.api.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class ApiResponse<T> {

    private HttpStatus status;

    private String message;

    private T data;

    public ApiResponse(HttpStatus status, String message){
        this.status = status;
        this.message = message;
        this.data = null;
    }
}