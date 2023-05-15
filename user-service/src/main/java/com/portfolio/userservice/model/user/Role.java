package com.portfolio.userservice.model.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_UNAUTHORIZED,
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_SUPER_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
