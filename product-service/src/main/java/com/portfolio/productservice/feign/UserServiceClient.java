package com.portfolio.productservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-service")
public interface UserServiceClient {
    @GetMapping("/user-service/{userId}/company")
     checkOwner(@PathVariable String userId);

}
