package com.portfolio.productservice.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.portfolio.productservice.model.product.clothes.ClothesType;
import com.portfolio.productservice.model.product.clothes.LengthType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCompany {
    private Long id;
    private Integer stock;
    private LocalDateTime registerDate;
    private String companyName;
    private String productId;
    @Enumerated(EnumType.STRING)
    private LengthType lengthType;
    @Enumerated(EnumType.STRING)
    private ClothesType clothesType;
    private int price;
}
