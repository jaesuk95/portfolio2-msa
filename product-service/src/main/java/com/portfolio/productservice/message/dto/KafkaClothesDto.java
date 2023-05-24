package com.portfolio.productservice.message.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaClothesDto {
    private String lengthType;
    private String clothesType;
    private Long product_entity_id;
}
