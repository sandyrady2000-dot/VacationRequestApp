package com.example.VacationRequest.business_core.service.impl;

import com.example.VacationRequest.business_core.dto.Response;
import com.example.VacationRequest.business_core.entity.VacationRequest;
import com.example.VacationRequest.business_core.enums.VacationStatus;
import com.example.VacationRequest.business_core.mapper.VacationMapper;
import com.example.VacationRequest.business_core.repository.VacationRequestRepository;
import com.example.VacationRequest.business_core.service.VacationManagerService;
import com.example.VacationRequest.usermgmt.entity.User;
import com.example.VacationRequest.usermgmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VacationManagerServiceImpl implements VacationManagerService {

    private final VacationRequestRepository vacationRepo;
    private final UserRepository userRepo;
    private final VacationMapper vacationMapper;

    private User currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Response> getPendingRequests() {
        User manager = currentUser();

        return vacationRepo
                .findByManager_IdAndStatusOrderByCreatedAtDesc(manager.getId(), VacationStatus.PENDING)
                .stream()
                .map(vacationMapper::toResponse)
                .toList();
    }

    @Override
    public Response approve(Long requestId) {
        User manager = currentUser();

        VacationRequest req = vacationRepo.findByIdAndManager_Id(requestId, manager.getId())
                .orElseThrow(() -> new AccessDeniedException("Request not found or not yours"));

        if (req.getStatus() != VacationStatus.PENDING) {
            throw new IllegalStateException("Request already processed");
        }

        req.setStatus(VacationStatus.APPROVED);
        VacationRequest saved = vacationRepo.save(req);

        return vacationMapper.toResponse(saved);
    }

    @Override
    public Response reject(Long requestId) {
        User manager = currentUser();

        VacationRequest req = vacationRepo.findByIdAndManager_Id(requestId, manager.getId())
                .orElseThrow(() -> new AccessDeniedException("Request not found or not yours"));

        if (req.getStatus() != VacationStatus.PENDING) {
            throw new IllegalStateException("Request already processed");
        }

        req.setStatus(VacationStatus.REJECTED);
        VacationRequest saved = vacationRepo.save(req);

        return vacationMapper.toResponse(saved);
    }
}