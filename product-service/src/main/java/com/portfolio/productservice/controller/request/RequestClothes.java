package com.portfolio.productservice.controller.request;

import com.portfolio.productservice.model.product.clothes.ClothesType;
import com.portfolio.productservice.model.product.clothes.LengthType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RequestClothes {
    private Integer stock;
    private LocalDateTime registeredDate;
    @Enumerated(EnumType.STRING)
    private LengthType lengthType;
    @Enumerated(EnumType.STRING)
    private ClothesType clothesType;
    private int price;
    private String user_id;
    private String companyName;
}
