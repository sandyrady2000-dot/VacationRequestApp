package com.example.VacationRequest.auth.service.impl;

import com.example.VacationRequest.auth.dto.request.LoginRequest;
import com.example.VacationRequest.auth.dto.request.RefreshRequest;
import com.example.VacationRequest.auth.dto.response.AuthResponse;
import com.example.VacationRequest.auth.service.AuthService;
import com.example.VacationRequest.common.exception.UserNotFoundException;
import com.example.VacationRequest.security.JwtService;
import com.example.VacationRequest.security.CustomUserDetailsService;
import com.example.VacationRequest.usermgmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepo;

    // ✅ used for refresh to load authorities correctly
    private final CustomUserDetailsService userDetailsService;

    @Override
    public AuthResponse login(LoginRequest request) {

        // 1) authenticate
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2) principal (UserDetails)
        UserDetails principal = (UserDetails) authentication.getPrincipal();

        // 3) load user from DB for mustChangePassword flag
        var user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        // 4) rememberMe -> refresh token TTL
        boolean remember = Boolean.TRUE.equals(request.getRememberMe());
        Duration refreshTtl = jwtService.refreshDuration(remember);

        // 5) generate tokens
        String accessToken = jwtService.generateAccessToken(principal);
        String refreshToken = jwtService.generateRefreshToken(principal, refreshTtl);

        // 6) response
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .roles(principal.getAuthorities().stream().map(a -> a.getAuthority()).toList())
                .accessExpiresInSeconds(jwtService.getAccessTokenTtlSeconds())
                .refreshExpiresInSeconds(refreshTtl.toSeconds())
                .mustChangePassword(user.isMustChangePassword())
                .build();
    }

    @Override
    public AuthResponse refresh(RefreshRequest request) {

        String refreshToken = request.getRefreshToken();

        // 1) get email from refresh token
        String email = jwtService.extractEmail(refreshToken);

        // 2) load user + userdetails (to validate + build roles)
        var user = userRepo.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // 3) validate refresh token
        if (!jwtService.isRefreshTokenValid(refreshToken, userDetails)) {
            throw new RuntimeException("Invalid refresh token");
        }

        // 4) issue new access token
        String newAccessToken = jwtService.generateAccessToken(userDetails);

        // Note: we keep same refresh token (no rotation for now)
        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .roles(userDetails.getAuthorities().stream().map(a -> a.getAuthority()).toList())
                .accessExpiresInSeconds(jwtService.getAccessTokenTtlSeconds())
                .refreshExpiresInSeconds(0) // optional; you can decode from token if you want
                .mustChangePassword(user.isMustChangePassword())
                .build();
    }
}