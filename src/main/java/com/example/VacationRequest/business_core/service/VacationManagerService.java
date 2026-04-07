package com.example.VacationRequest.business_core.service;

import com.example.VacationRequest.business_core.dto.Response;

import java.util.List;

public interface VacationManagerService {

    List<Response> getPendingRequests();

    Response approve(Long requestId);

    Response reject(Long requestId);
}