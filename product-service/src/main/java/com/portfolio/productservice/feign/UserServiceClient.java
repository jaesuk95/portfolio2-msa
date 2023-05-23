package com.portfolio.productservice.feign;

import com.portfolio.productservice.controller.response.ResponseCompany;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("user-service")
public interface UserServiceClient {
    @GetMapping("/user-service/{userId}/company")
    List<ResponseCompany> companyValidation(@PathVariable String userId);

}
