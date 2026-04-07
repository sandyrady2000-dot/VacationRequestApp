package com.example.VacationRequest.common.exception;

import com.example.VacationRequest.common.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private final ErrorCode code;
    private final HttpStatus status;

    public ApiException(ErrorCode code, HttpStatus status, String message) {
        super(message);
        this.code = code;
        this.status = status;
    }
}