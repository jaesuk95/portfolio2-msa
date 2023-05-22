package com.portfolio.productservice.model.product.clothes.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ClothesDto {
    private String brandName;
    private String companyRegistered;
    private Integer stock;
    private LocalDateTime registeredDate;
    private String lengthType;
    private String clothesType;
}
