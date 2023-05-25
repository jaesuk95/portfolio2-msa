package com.portfolio.productservice.message.dto.clothes;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payload {
    private String clothes_type;
    private String length_type;
    private Long product_entity_id;
}
