package com.example.VacationRequest.business_core.controller;

import com.example.VacationRequest.business_core.dto.CreateRequest;
import com.example.VacationRequest.business_core.dto.Response;
import com.example.VacationRequest.business_core.service.VacationEmployeeService;
import com.example.VacationRequest.usermgmt.dto.response.PersonalInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacations")
@RequiredArgsConstructor
public class VacationEmployeeController {

    private final VacationEmployeeService employeeService;

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    @PostMapping
    public Response create(@RequestBody CreateRequest req) {
        System.out.println("DTO halfDay = " + req.isHalfDay()); // 👈 here
        return employeeService.createRequest(req);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    @GetMapping("/my")
    public List<Response> my() {
        return employeeService.getMyRequests();
    }

    @GetMapping("/my-balance")
    public PersonalInfoResponse getMyBalance() {
        return employeeService.getMyBalance();
    }
}