package com.example.VacationRequest.usermgmt.controller;

import com.example.VacationRequest.usermgmt.dto.request.UserCreateRequest;
import com.example.VacationRequest.usermgmt.dto.request.UserUpdateRequest;
import com.example.VacationRequest.usermgmt.dto.response.UserResponse;
import com.example.VacationRequest.usermgmt.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody UserCreateRequest request) {
        return userService.create(request);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        return userService.update(id, request);
    }

    @PatchMapping("/{id}/disable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disable(@PathVariable Long id) {
        userService.disable(id);
    }
    @GetMapping("/by-name")
    public ResponseEntity<UserResponse> getByName(
            @RequestParam String name
    ) {
        return ResponseEntity.ok(userService.getByName(name));
    }

}
