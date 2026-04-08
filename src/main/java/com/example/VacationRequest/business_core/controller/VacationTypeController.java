package com.example.VacationRequest.business_core.controller;

import com.example.VacationRequest.business_core.entity.VacationType;
import com.example.VacationRequest.business_core.service.VacationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacation-types")
@RequiredArgsConstructor
public class VacationTypeController {

    private final VacationTypeService vacationTypeService;

    @GetMapping
    public List<VacationType> getAllVacationTypes() {
        return vacationTypeService.getAllVacationTypes();
    }
}