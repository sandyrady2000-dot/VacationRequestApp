package com.example.VacationRequest.common.exception;

import com.example.VacationRequest.common.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, Object> details = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fe.getField(), fe.getDefaultMessage());
        }
        details.put("fieldErrors", fieldErrors);

        return build(HttpStatus.BAD_REQUEST, ErrorCode.VALIDATION_FAILED, "Validation failed", req, details);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFound(UserNotFoundException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND, ex.getMessage(), req, null);
    }

    @ExceptionHandler(CurrentPasswordIncorrectException.class)
    public ResponseEntity<ApiErrorResponse> handleWrongCurrent(CurrentPasswordIncorrectException ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, ErrorCode.CURRENT_PASSWORD_INCORRECT, ex.getMessage(), req, null);
    }

    @ExceptionHandler(PasswordTooShortException.class)
    public ResponseEntity<ApiErrorResponse> handleTooShort(
            PasswordTooShortException ex,
            HttpServletRequest req
    ) {
        return build(HttpStatus.BAD_REQUEST,
                ErrorCode.PASSWORD_TOO_SHORT,
                ex.getMessage(),
                req,
                null);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCred(BadCredentialsException ex, HttpServletRequest req) {
        return build(HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_CREDENTIALS, "Invalid email or password", req, null);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleForbidden(AccessDeniedException ex, HttpServletRequest req) {
        return build(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN, "You don't have permission", req, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAny(Exception ex, HttpServletRequest req) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_ERROR, "Unexpected error", req, null);
    }

    private ResponseEntity<ApiErrorResponse> build(HttpStatus status, ErrorCode name, String message,
                                           HttpServletRequest req, Map<String, Object> details) {
        ApiErrorResponse body = new ApiErrorResponse(
                name,
                message,
                status.value(),
                req.getRequestURI(),
                Instant.now(),
                details
        );
        return ResponseEntity.status(status).body(body);
    }
}