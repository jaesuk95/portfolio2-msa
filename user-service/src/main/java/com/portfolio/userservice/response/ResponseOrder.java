package com.portfolio.userservice.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
//    private Date createdAt;
    private String orderId;
}
