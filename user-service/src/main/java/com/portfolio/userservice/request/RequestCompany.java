package com.portfolio.userservice.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestCompany {
    private String companyName;
    private String brandName;
    private String userId;
}
