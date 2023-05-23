package com.portfolio.productservice.message.dto;

import com.portfolio.productservice.model.product.clothes.ClothesType;
import com.portfolio.productservice.model.product.clothes.LengthType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payload {

    @Enumerated(EnumType.STRING)
    private ClothesType clothes_type;
    @Enumerated(EnumType.STRING)
    private LengthType length_type;
    private int stock;
    private LocalDateTime registered_date;
    private int price;
    private String user_id;
    private String company_name;
    private String product_id;
}
