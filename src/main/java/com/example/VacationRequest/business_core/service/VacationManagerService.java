package com.example.VacationRequest.business_core.service;

import com.example.VacationRequest.business_core.dto.Response;
import com.example.VacationRequest.business_core.dto.VacationDecisionRequest;

import java.util.List;

public interface VacationManagerService {

    List<Response> getPendingRequests();

    Response decide(Long id, VacationDecisionRequest request);
}