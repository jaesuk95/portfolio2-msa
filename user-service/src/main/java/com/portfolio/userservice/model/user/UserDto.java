package com.portfolio.userservice.model.user;

import com.portfolio.userservice.response.ResponseCompany;
import com.portfolio.userservice.response.ResponseOrder;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private String name;
    private String userId;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private Role role;

    private List<ResponseOrder> orders;
    private List<ResponseCompany> companies;

}
