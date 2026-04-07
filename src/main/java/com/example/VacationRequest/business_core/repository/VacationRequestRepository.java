package com.example.VacationRequest.business_core.repository;

import com.example.VacationRequest.business_core.dto.VacationRequestAdminView;
import com.example.VacationRequest.business_core.entity.VacationRequest;
import com.example.VacationRequest.business_core.enums.VacationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long> {

    List<VacationRequest> findByEmployee_IdOrderByCreatedAtDesc(Long employeeId);

    List<VacationRequest> findByManager_IdAndStatusOrderByCreatedAtDesc(Long managerId, VacationStatus status);

    Optional<VacationRequest> findByIdAndManager_Id(Long id, Long managerId);

    @Query("""
        select new com.example.VacationRequest.business_core.dto.VacationRequestAdminView(
            vr.id,
            vr.startDate,
            vr.endDate,
            vr.reason,
            vr.status,
            vr.createdAt,
            e.id,
            e.email,
            m.id,
            m.email
        )
        from VacationRequest vr
        left join vr.employee e
        left join vr.manager m
        order by vr.createdAt desc
    """)
    List<VacationRequestAdminView> findAllForAdmin();
}
