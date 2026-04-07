package com.example.VacationRequest.common.exception;

import com.example.VacationRequest.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND, "User not found");
    }

}