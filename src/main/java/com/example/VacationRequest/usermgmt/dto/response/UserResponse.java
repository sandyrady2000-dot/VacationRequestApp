package com.example.VacationRequest.usermgmt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String fullName;
    private boolean enabled;
    private RoleResponse role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
