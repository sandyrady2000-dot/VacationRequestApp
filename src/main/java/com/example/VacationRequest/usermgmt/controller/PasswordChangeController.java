package com.example.VacationRequest.usermgmt.controller;

import com.example.VacationRequest.usermgmt.dto.request.ChangePasswordRequest;
import com.example.VacationRequest.usermgmt.dto.response.ChangePasswordResponse;
import com.example.VacationRequest.usermgmt.service.PasswordChangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/password")
public class PasswordChangeController {

    private final PasswordChangeService passwordChangeService;

    @PostMapping("/change")
    public ResponseEntity<ChangePasswordResponse> changePassword(@Valid @RequestBody ChangePasswordRequest req) {
        passwordChangeService.changePassword(req.currentPassword(), req.newPassword());
        return ResponseEntity.ok(new ChangePasswordResponse("Password updated successfully"));
    }
}