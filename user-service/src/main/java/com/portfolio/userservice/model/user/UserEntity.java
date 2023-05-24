package com.portfolio.userservice.model.user;

import com.portfolio.userservice.model.address.AddressEntity;
import com.portfolio.userservice.model.company.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false, length = 50, unique = true)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String userId;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity")
    private List<Company> companies = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<AddressEntity> address = new ArrayList<>();

    private LocalDateTime createdAt;

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public UserEntity(String email, String name, String password, String userId, Role role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.userId = userId;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    // Assuming Role implements the GrantedAuthority interface
        return Collections.singletonList(role);
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
