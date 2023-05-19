package com.portfolio.orderservice.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
}
