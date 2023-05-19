package com.portfolio.orderservice.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
}
