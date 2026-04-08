package com.example.VacationRequest.business_core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "vacation_types")
@Data
public class VacationType {
    @Id
    private Long id;

    private String name;
}
