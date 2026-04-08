package com.example.VacationRequest.business_core.repository;

import com.example.VacationRequest.business_core.entity.VacationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacationTypeRepository extends JpaRepository<VacationType, Long> {
}