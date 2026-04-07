package com.example.VacationRequest.usermgmt.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    @Email
    private String email;

    @Size(min = 8, max = 72)
    private String password;

    @Size(max = 150)
    private String fullName;

    private Long roleId;

    private Boolean enabled;
}
