package com.portfolio.orderservice.feign;

import com.portfolio.orderservice.controller.response.ResponseProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("product-service")
public interface ProductServiceClient {
    @GetMapping("/product-service/{productId}")
    ResponseProduct productAvailable(@PathVariable String productId);
}
