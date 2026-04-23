package com.example.VacationRequest.business_core.dto;

import com.example.VacationRequest.business_core.enums.VacationAction;
import lombok.Data;

@Data
public class VacationDecisionRequest {
    private VacationAction action; // APPROVE / REJECT
    private String comment;

}
