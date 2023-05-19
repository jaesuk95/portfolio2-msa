package com.portfolio.orderservice.controller;

import com.portfolio.orderservice.controller.request.RequestOrder;
import com.portfolio.orderservice.controller.response.ResponseOrder;
import com.portfolio.orderservice.model.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{userId}/order")
    public ResponseEntity<ResponseOrder> createOrder(
            @PathVariable("userId") String userId,
            @RequestBody RequestOrder requestOrder) {
        log.info("user order register request");
        ResponseOrder responseOrder = orderService.registerUserOrder(requestOrder, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }


}
