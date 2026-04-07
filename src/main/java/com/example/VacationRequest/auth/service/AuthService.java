package com.example.VacationRequest.auth.service;

import com.example.VacationRequest.auth.dto.request.LoginRequest;
import com.example.VacationRequest.auth.dto.request.RefreshRequest;
import com.example.VacationRequest.auth.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);

    AuthResponse refresh(RefreshRequest request);
}
