package com.example.VacationRequest.common.exception;

import com.example.VacationRequest.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class CurrentPasswordIncorrectException extends ApiException {
    public CurrentPasswordIncorrectException() {
        super(ErrorCode.CURRENT_PASSWORD_INCORRECT, HttpStatus.BAD_REQUEST, "Current password is incorrect");
    }
}