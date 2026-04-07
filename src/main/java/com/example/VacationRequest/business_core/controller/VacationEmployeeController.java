package com.example.VacationRequest.business_core.controller;

import com.example.VacationRequest.business_core.dto.CreateRequest;
import com.example.VacationRequest.business_core.dto.Response;
import com.example.VacationRequest.business_core.service.VacationEmployeeService;
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
        return employeeService.createRequest(req);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    @GetMapping("/my")
    public List<Response> my() {
        return employeeService.getMyRequests();
    }
}