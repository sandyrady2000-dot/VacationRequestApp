package com.example.VacationRequest.business_core.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class Response {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long employeeId;
    private String employeeEmail;

    private Long managerId;
    private String managerEmail;

    private Long vacationTypeId;
    private String vacationTypeName;
    private boolean halfDay;
}