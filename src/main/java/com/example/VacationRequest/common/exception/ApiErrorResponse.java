package com.example.VacationRequest.common.exception;

import com.example.VacationRequest.common.ErrorCode;

import java.time.Instant;
import java.util.Map;

public record ApiErrorResponse(
        ErrorCode error,
        String message,
        int status,
        String path,
        Instant timestamp,
        Map<String, Object> details
) {}