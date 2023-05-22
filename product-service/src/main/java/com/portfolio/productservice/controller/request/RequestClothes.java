package com.portfolio.productservice.controller.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestClothes {
    private String brandName;
    private String companyRegistered;
    private Integer stock;
    private LocalDateTime registeredDate;
    private String lengthType;
    private String clothesType;
}
