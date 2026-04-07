package com.example.VacationRequest.usermgmt.service;

import com.example.VacationRequest.usermgmt.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse create(String name, String description);

    RoleResponse getById(Long id);

    List<RoleResponse> getAll();

    void delete(Long id);
}
