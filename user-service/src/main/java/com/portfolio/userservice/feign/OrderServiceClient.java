package com.portfolio.userservice.feign;

import com.portfolio.userservice.response.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//@FeignClient(name = "order-service")
@FeignClient(
        name = "order-service",
        url = "${app.orderurl}"
)
public interface OrderServiceClient {
//    @GetMapping("/order-service/{userId}/orders")
    @GetMapping("/{userId}/orders")
    List<ResponseOrder> getOrders(@PathVariable String userId);
}
