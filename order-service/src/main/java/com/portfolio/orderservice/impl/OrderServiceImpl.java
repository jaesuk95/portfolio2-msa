package com.portfolio.orderservice.impl;

import com.portfolio.orderservice.controller.request.RequestOrder;
import com.portfolio.orderservice.controller.response.ResponseOrder;
import com.portfolio.orderservice.message.kafka.producer.KafkaOrderProducer;
import com.portfolio.orderservice.model.OrderDto;
import com.portfolio.orderservice.model.OrderEntity;
import com.portfolio.orderservice.model.OrderRepository;
import com.portfolio.orderservice.model.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final KafkaOrderProducer kafkaOrderProducer;
    private final OrderRepository orderRepository;

    @Override
    public ResponseOrder registerUserOrder(RequestOrder requestOrder, String userId) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = modelMapper.map(requestOrder, OrderDto.class);
        orderDto.setUserId(userId);

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
