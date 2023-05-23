package com.portfolio.orderservice.impl;

import com.portfolio.orderservice.controller.request.RequestOrder;
import com.portfolio.orderservice.controller.response.ResponseOrder;
import com.portfolio.orderservice.controller.response.ResponseProduct;
import com.portfolio.orderservice.feign.ProductServiceClient;
import com.portfolio.orderservice.message.kafka.producer.KafkaOrderProducer;
import com.portfolio.orderservice.model.OrderDto;
import com.portfolio.orderservice.model.OrderEntity;
import com.portfolio.orderservice.model.OrderRepository;
import com.portfolio.orderservice.model.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final KafkaOrderProducer kafkaOrderProducer;
    private final OrderRepository orderRepository;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final ProductServiceClient productServiceClient;

    @Override
    public ResponseOrder registerUserOrder(RequestOrder requestOrder) {
        String user_id = requestOrder.getUser_id();
        String productId = requestOrder.getProductId();

        log.info("Before call orders microservice");
        CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitBreaker");
        ResponseProduct responseProduct = (ResponseProduct) circuitbreaker.run(() -> productServiceClient.productAvailable(productId),
                throwable -> new ArrayList<>());// <- throwable -> new ArrayList<>() 이 코드의 뜻은, orderServiceClient.getOrders(id) 에서 오류가 발생하면 비어있는 arrayList[] 으로 반환한다는 뜻이다.
        log.info("After called orders microservice");

        // if the product is out of stock
        if (responseProduct.getStatus() == 400) {
            ResponseOrder responseOrder = new ResponseOrder();
            responseOrder.setMessage(responseOrder.getMessage());
            responseOrder.setStatus(responseOrder.getStatus());
            return responseOrder;
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = modelMapper.map(requestOrder, OrderDto.class);
        orderDto.setUser_id(user_id);


        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(requestOrder.getQty() * requestOrder.getUnitPrice());
        ResponseOrder returnValue = modelMapper.map(orderDto, ResponseOrder.class);

        // send an order to kafka
        kafkaOrderProducer.send("orders", orderDto);
        return returnValue;
    }

    @Override
    public Iterable<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
