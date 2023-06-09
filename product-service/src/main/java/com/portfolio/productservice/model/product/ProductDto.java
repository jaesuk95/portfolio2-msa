package com.portfolio.productservice.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private int stock;
    //    private LocalDateTime registeredDate;
    private int price;
    private String userId;
    private String companyName;
    private String productId;
    private String type;
}
