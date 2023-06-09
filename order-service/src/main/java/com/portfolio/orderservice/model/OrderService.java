package com.portfolio.orderservice.model;

import com.portfolio.orderservice.controller.request.RequestOrder;
import com.portfolio.orderservice.controller.response.ResponseOrder;

public interface OrderService {
    ResponseOrder registerUserOrder(RequestOrder requestOrder);

    Iterable<OrderEntity> getOrdersByUserId(String userId);
}
