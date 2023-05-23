package com.portfolio.productservice.model.product.clothes.dto;

import com.portfolio.productservice.model.product.clothes.ClothesType;
import com.portfolio.productservice.model.product.clothes.LengthType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClothesDto {
    private Long id;
    private Integer stock;
    private LocalDateTime registeredDate;
    @Enumerated(EnumType.STRING)
    private LengthType lengthType;
    @Enumerated(EnumType.STRING)
    private ClothesType clothesType;
    private int price;
    private String companyName;
    private String user_id;
}
