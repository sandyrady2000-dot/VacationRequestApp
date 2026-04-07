package com.example.VacationRequest.business_core.service;

import com.example.VacationRequest.business_core.dto.CreateRequest;
import com.example.VacationRequest.business_core.dto.Response;

import java.util.List;

public interface VacationEmployeeService {
    Response createRequest(CreateRequest req);
    List<Response> getMyRequests();
}