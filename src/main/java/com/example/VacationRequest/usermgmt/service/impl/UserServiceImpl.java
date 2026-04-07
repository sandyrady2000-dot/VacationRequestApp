package com.example.VacationRequest.usermgmt.service.impl;

import com.example.VacationRequest.usermgmt.dto.request.UserCreateRequest;
import com.example.VacationRequest.usermgmt.dto.request.UserUpdateRequest;
import com.example.VacationRequest.usermgmt.dto.response.UserResponse;
import com.example.VacationRequest.usermgmt.entity.User;
import com.example.VacationRequest.usermgmt.mapper.UserMapper;
import com.example.VacationRequest.usermgmt.repository.RoleRepository;
import com.example.VacationRequest.usermgmt.repository.UserRepository;
import com.example.VacationRequest.usermgmt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//@PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public UserResponse create(UserCreateRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }

        var role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + request.getRoleId()));

        // Builder create entity
        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .enabled(request.getEnabled() != null ? request.getEnabled() : true)
                .build();

        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public UserResponse getById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));

        return userMapper.toResponse(user);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @Transactional(readOnly = true)
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public UserResponse update(Long id, UserUpdateRequest request) {

        var user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));

        // Map simple fields
        userMapper.updateEntity(request, user);

        // Update password if provided
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        // Update role if provided
        if (request.getRoleId() != null) {
            var role = roleRepository.findById(request.getRoleId())
                    .orElseThrow(() -> new IllegalArgumentException("Role not found: " + request.getRoleId()));
            user.setRole(role);
        }

        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public void disable(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));

        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Transactional(readOnly = true)
    public UserResponse getByName(String name) {

        String normalized = name == null ? null : name.trim().replaceAll("\\s+", " ");

        var user = userRepository
                .findFirstByFullNameContainingIgnoreCase(name)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + name));

        return userMapper.toResponse(user);
    }
}
