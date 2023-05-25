package com.portfolio.productservice.message.dto.product;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payload {
    private int stock;
    private int price;
    private String user_id;
    private String company_name;
    private String product_id;
    private String type;
}
