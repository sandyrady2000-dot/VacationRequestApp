package com.example.VacationRequest.business_core.service;

import com.example.VacationRequest.business_core.dto.CreateRequest;
import com.example.VacationRequest.business_core.dto.Response;
import com.example.VacationRequest.usermgmt.dto.response.PersonalInfoResponse;

import java.util.List;

public interface VacationEmployeeService {
    Response createRequest(CreateRequest req);
    List<Response> getMyRequests();
    PersonalInfoResponse getMyBalance();
}