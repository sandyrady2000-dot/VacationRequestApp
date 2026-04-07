package com.example.VacationRequest.business_core.service.impl;

import com.example.VacationRequest.business_core.dto.VacationRequestAdminView;
import com.example.VacationRequest.business_core.repository.VacationRequestRepository;
import com.example.VacationRequest.business_core.service.VacationAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VacationAdminServiceImpl implements VacationAdminService {

    private final VacationRequestRepository vacationRequestRepository;

    @Override
    public List<VacationRequestAdminView> viewAllRequests() {
        return vacationRequestRepository.findAllForAdmin();
    }
}