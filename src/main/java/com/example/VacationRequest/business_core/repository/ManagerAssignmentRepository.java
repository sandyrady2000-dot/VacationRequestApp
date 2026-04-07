package com.example.VacationRequest.business_core.repository;

import com.example.VacationRequest.business_core.entity.ManagerAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerAssignmentRepository extends JpaRepository<ManagerAssignment, Long> {
    Optional<ManagerAssignment> findByEmployeeUserId(Long employeeUserId);

}