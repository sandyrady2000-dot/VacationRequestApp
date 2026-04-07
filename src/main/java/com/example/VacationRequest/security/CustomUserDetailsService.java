package com.example.VacationRequest.security;

import com.example.VacationRequest.usermgmt.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final Collection<? extends GrantedAuthority> authorities;
    private final UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var user = userRepository.findByEmailWithRole(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        if (Boolean.FALSE.equals(user.getEnabled())) {
            throw new DisabledException("User is disabled");
        }

        // DB has ONE role (EMPLOYEE / MANAGER / SUPER_ADMIN)
        // but we "expand" it into multiple authorities:
        // MANAGER => ROLE_MANAGER + ROLE_EMPLOYEE
        // SUPER_ADMIN => ROLE_SUPER_ADMIN + ROLE_MANAGER + ROLE_EMPLOYEE
        // EMPLOYEE => ROLE_EMPLOYEE
        String roleName = user.getRole().getName();

        List<SimpleGrantedAuthority> authorities = switch (roleName) {
            case "SUPER_ADMIN" -> List.of(
                    new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_MANAGER"),
                    new SimpleGrantedAuthority("ROLE_EMPLOYEE")
            );
            case "MANAGER" -> List.of(
                    new SimpleGrantedAuthority("ROLE_MANAGER"),
                    new SimpleGrantedAuthority("ROLE_EMPLOYEE")
            );
            default -> List.of(
                    new SimpleGrantedAuthority("ROLE_EMPLOYEE")
            );
        };

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                authorities
        );
    }
}