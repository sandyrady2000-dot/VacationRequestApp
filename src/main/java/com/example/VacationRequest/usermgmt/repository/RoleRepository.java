package com.example.VacationRequest.usermgmt.repository;

import com.example.VacationRequest.usermgmt.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
