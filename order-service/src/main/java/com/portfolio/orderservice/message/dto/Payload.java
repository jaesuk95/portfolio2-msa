package com.portfolio.orderservice.message.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payload {
    private String order_id;
    private String user_id;
    private String product_id;
    private int qty;
    private int total_price;
    private int unit_price;
}
