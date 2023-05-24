package com.portfolio.productservice.message.dto.payload;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPayload {
    private int stock;
    private int price;
    private String user_id;
    private String company_name;
    private String product_id;
    private String type;
}
