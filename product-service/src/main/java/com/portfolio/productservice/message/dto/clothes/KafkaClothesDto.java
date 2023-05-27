package com.portfolio.productservice.message.dto.clothes;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaClothesDto {
    private String lengthType;
    private String clothesType;
    private Long product_entity_id;
}
