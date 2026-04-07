package com.example.VacationRequest.business_core.controller;


import com.example.VacationRequest.business_core.dto.Response;
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

    // Manager only: approve
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/approve")
    public Response approve(@PathVariable Long id) {
        return managerService.approve(id);
    }

    // Manager only: reject
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/reject")
    public Response reject(@PathVariable Long id) {
        return managerService.reject(id);
    }
}