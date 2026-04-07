package com.example.VacationRequest.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final String email;
    private final String passwordHash;
    private final boolean mustChangePassword;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(
            String email,
            String passwordHash,
            boolean mustChangePassword,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.mustChangePassword = mustChangePassword;
        this.authorities = authorities;
    }

    public boolean isMustChangePassword() {
        return mustChangePassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}