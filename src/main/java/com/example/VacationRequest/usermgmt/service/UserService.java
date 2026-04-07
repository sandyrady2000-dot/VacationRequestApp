package com.example.VacationRequest.usermgmt.service;

import com.example.VacationRequest.usermgmt.dto.request.UserCreateRequest;
import com.example.VacationRequest.usermgmt.dto.request.UserUpdateRequest;
import com.example.VacationRequest.usermgmt.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse create(UserCreateRequest request);
    UserResponse getById(Long id);
    List<UserResponse> getAll();
    UserResponse update(Long id, UserUpdateRequest request);
    void disable(Long id);
    UserResponse getByName(String name);
}
