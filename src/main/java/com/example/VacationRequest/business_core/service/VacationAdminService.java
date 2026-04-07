package com.example.VacationRequest.business_core.service;

import com.example.VacationRequest.business_core.dto.VacationRequestAdminView;

import java.util.List;

public interface VacationAdminService {
    List<VacationRequestAdminView> viewAllRequests();
}