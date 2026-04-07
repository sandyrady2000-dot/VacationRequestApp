package com.example.VacationRequest.business_core.entity;

import com.example.VacationRequest.usermgmt.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "manager_assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerAssignment {

    @Id
    @Column(name = "employee_user_id")
    private Long employeeUserId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "employee_user_id")
    private User employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_user_id", nullable = false)
    private User manager;
}