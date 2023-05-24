package com.portfolio.productservice.message.dto.payload;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClothesPayload {
    private String clothes_type;
    private String length_type;
    private Long product_entity_id;
}
