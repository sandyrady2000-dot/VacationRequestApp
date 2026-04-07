package com.example.VacationRequest.business_core.controller;

import com.example.VacationRequest.business_core.dto.VacationRequestAdminView;
import com.example.VacationRequest.business_core.entity.VacationRequest;
import com.example.VacationRequest.business_core.service.VacationAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/vacation-requests")
@RequiredArgsConstructor
public class VacationAdminController {

    private final VacationAdminService vacationAdminService;

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public List<VacationRequestAdminView> viewAllRequests() {
        return vacationAdminService.viewAllRequests();
    }
}