package com.example.VacationRequest.auth.dto.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType ;
    private List<String> roles;

    private long accessExpiresInSeconds;
    private long refreshExpiresInSeconds;

    private boolean mustChangePassword;

}
