package com.example.VacationRequest.auth.controller;

import com.example.VacationRequest.auth.dto.request.LoginRequest;
import com.example.VacationRequest.auth.dto.request.RefreshRequest;
import com.example.VacationRequest.auth.dto.response.AuthResponse;
import com.example.VacationRequest.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
    @PostMapping("/refresh")
    public AuthResponse refresh(@Valid @RequestBody RefreshRequest request) {
        return authService.refresh(request);
    }
}

