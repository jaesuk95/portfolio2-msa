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
//    private String order_id;
//    private String user_id;
//    private String product_id;
//    private int qty;
//    private int total_price;
//    private int unit_price;

    @Enumerated(EnumType.STRING)
    private ClothesType clothes_type;
    @Enumerated(EnumType.STRING)
    private LengthType length_type;
    private int stock;
    private LocalDateTime registered_date;
    private int price;
    private String user_id;
    private String company_name;
}
