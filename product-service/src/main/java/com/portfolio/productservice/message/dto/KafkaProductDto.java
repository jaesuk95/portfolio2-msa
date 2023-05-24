package com.portfolio.productservice.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaProductDto {
    private int stock;
    //    private LocalDateTime registeredDate;
    private int price;
    private String userId;
    private String companyName;
    private String productId;
    private String type;
}
