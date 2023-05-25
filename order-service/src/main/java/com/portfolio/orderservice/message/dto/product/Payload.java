package com.portfolio.orderservice.message.dto.product;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payload {
    private String product_id;
    private int stock;
}
