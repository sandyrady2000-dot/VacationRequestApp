package com.example.VacationRequest.usermgmt.service.impl;

import com.example.VacationRequest.usermgmt.dto.response.RoleResponse;
import com.example.VacationRequest.usermgmt.entity.Role;
import com.example.VacationRequest.usermgmt.mapper.RoleMapper;
import com.example.VacationRequest.usermgmt.repository.RoleRepository;
import com.example.VacationRequest.usermgmt.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse create(String name, String description) {

        String roleName = normalizeRoleName(name);

        if (roleRepository.existsByNameIgnoreCase(roleName)) {
            throw new IllegalArgumentException("Role already exists");
        }

        Role role = Role.builder()
                .name(roleName)
                .description(description)
                .build();

        Role saved = roleRepository.save(role);

        return roleMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getById(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        return roleMapper.toResponse(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> getAll() {

        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        if (!roleRepository.existsById(id)) {
            throw new IllegalArgumentException("Role not found");
        }

        roleRepository.deleteById(id);
    }

    private String normalizeRoleName(String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Role name is required");
        }

        String trimmed = name.trim().toUpperCase();

        if (!trimmed.startsWith("ROLE_")) {
            trimmed = "ROLE_" + trimmed;
        }

        return trimmed;
    }
}
