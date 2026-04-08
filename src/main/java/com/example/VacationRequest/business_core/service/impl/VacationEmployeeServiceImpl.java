package com.example.VacationRequest.business_core.service.impl;

import com.example.VacationRequest.business_core.dto.CreateRequest;
import com.example.VacationRequest.business_core.dto.Response;
import com.example.VacationRequest.business_core.entity.VacationRequest;
import com.example.VacationRequest.business_core.entity.VacationType;
import com.example.VacationRequest.business_core.enums.VacationStatus;
import com.example.VacationRequest.business_core.mapper.VacationMapper;
import com.example.VacationRequest.business_core.repository.VacationRequestRepository;
import com.example.VacationRequest.business_core.entity.ManagerAssignment;
import com.example.VacationRequest.business_core.repository.VacationTypeRepository;
import com.example.VacationRequest.business_core.service.VacationEmployeeService;
import com.example.VacationRequest.usermgmt.entity.User;
import com.example.VacationRequest.business_core.repository.ManagerAssignmentRepository;
import com.example.VacationRequest.usermgmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VacationEmployeeServiceImpl implements VacationEmployeeService {

    private final VacationRequestRepository vacationRepo;
    private final UserRepository userRepo;
    private final ManagerAssignmentRepository managerAssignRepo;
    private final VacationMapper vacationMapper;
    private final VacationTypeRepository vacationTypeRepo;

    private User currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }
    @Override
    public Response createRequest(CreateRequest req) {

        if (req.getStartDate() == null || req.getEndDate() == null) {
            throw new IllegalArgumentException("startDate and endDate are required");
        }
        if (req.getStartDate().isAfter(req.getEndDate())) {
            throw new IllegalArgumentException("startDate must be <= endDate");
        }
        if (req.getVacationTypeId() == null) {
            throw new IllegalArgumentException("vacationTypeId is required");
        }

        User employee = currentUser();

        ManagerAssignment assignment = managerAssignRepo.findByEmployeeUserId(employee.getId())
                .orElseThrow(() -> new RuntimeException("No manager assigned for employee_id=" + employee.getId()));

        VacationType vacationType = vacationTypeRepo.findById(req.getVacationTypeId())
                .orElseThrow(() -> new RuntimeException("Vacation type not found: " + req.getVacationTypeId()));

        VacationRequest entity = vacationMapper.toEntity(req);
        entity.setEmployee(employee);
        entity.setManager(assignment.getManager());
        entity.setStatus(VacationStatus.PENDING);
        entity.setVacationType(vacationType);

        VacationRequest saved = vacationRepo.save(entity);
        return vacationMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Response> getMyRequests() {
        User employee = currentUser();
        return vacationRepo.findByEmployee_IdOrderByCreatedAtDesc(employee.getId())
                .stream()
                .map(vacationMapper::toResponse)
                .toList();
    }

}