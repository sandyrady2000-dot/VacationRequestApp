package com.example.VacationRequest.common.exception;

public class PasswordTooShortException extends RuntimeException {

    public PasswordTooShortException() {

        super("Password must be at least 8 characters");
    }
}