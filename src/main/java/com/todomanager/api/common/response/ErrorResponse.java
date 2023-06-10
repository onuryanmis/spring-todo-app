package com.todomanager.api.common.response;

import com.todomanager.api.common.definition.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private HttpStatus status;

    private ErrorCode code;

    private String[] messages;

    private LocalDateTime timestamp;
}