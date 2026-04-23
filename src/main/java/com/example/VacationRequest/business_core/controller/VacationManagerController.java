package com.example.VacationRequest.business_core.controller;


import com.example.VacationRequest.business_core.dto.Response;
import com.example.VacationRequest.business_core.dto.VacationDecisionRequest;
import com.example.VacationRequest.business_core.service.VacationManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacations")
@RequiredArgsConstructor
public class VacationManagerController {

    private final VacationManagerService managerService;

    // Manager only: view pending requests assigned to him
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/pending")
    public List<Response> pending() {
        return managerService.getPendingRequests();
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/decision")
    public Response decide(
            @PathVariable Long id,
            @RequestBody VacationDecisionRequest request
    ) {
        return managerService.decide(id, request);
    }
}