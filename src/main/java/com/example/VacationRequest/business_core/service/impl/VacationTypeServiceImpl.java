package com.example.VacationRequest.business_core.service.impl;

import com.example.VacationRequest.business_core.entity.VacationType;
import com.example.VacationRequest.business_core.repository.VacationTypeRepository;
import com.example.VacationRequest.business_core.service.VacationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationTypeServiceImpl implements VacationTypeService {

    private final VacationTypeRepository vacationTypeRepository;

    @Override
    public List<VacationType> getAllVacationTypes() {
        return vacationTypeRepository.findAll();
    }
}