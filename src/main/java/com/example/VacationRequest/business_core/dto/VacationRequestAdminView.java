package com.example.VacationRequest.business_core.dto;

import com.example.VacationRequest.business_core.enums.VacationStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class VacationRequestAdminView {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private VacationStatus status;
    private LocalDateTime createdAt;

    private Long employeeId;
    private String employeeEmail;

    private Long managerId;
    private String managerEmail;
}