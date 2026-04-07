package com.example.VacationRequest.usermgmt.service.impl;

import com.example.VacationRequest.common.exception.CurrentPasswordIncorrectException;
import com.example.VacationRequest.common.exception.UserNotFoundException;
import com.example.VacationRequest.usermgmt.repository.UserRepository;
import com.example.VacationRequest.usermgmt.service.PasswordChangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PasswordChangeServiceImpl implements PasswordChangeService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void changePassword(String currentPassword, String newPassword) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        var user = userRepo.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(currentPassword, user.getPasswordHash())) {
            throw new CurrentPasswordIncorrectException();
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setMustChangePassword(false);

        userRepo.save(user);
    }
}