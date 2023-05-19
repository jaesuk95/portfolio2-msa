package com.portfolio.orderservice.controller;

import com.portfolio.orderservice.controller.request.RequestOrder;
import com.portfolio.orderservice.controller.response.ResponseOrder;
import com.portfolio.orderservice.model.OrderEntity;
import com.portfolio.orderservice.model.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId) throws Exception {
        log.info("Before retrieve orders data");
        Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });

//        try {
//            Thread.sleep(3000);
//            throw new Exception("TESTING 장애 발생");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        log.info("After retrieved orders Data");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
