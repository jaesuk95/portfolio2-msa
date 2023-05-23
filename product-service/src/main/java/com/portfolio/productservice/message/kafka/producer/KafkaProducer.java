package com.portfolio.productservice.message.kafka.producer;//package com.portfolio.orderservice.message.kafka.producer;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.portfolio.orderservice.model.OrderDto;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class KafkaProducer {
//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    public OrderDto send(String kafkaTopic, OrderDto orderDto) {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = "";
//        try {
//            jsonInString = mapper.writeValueAsString(orderDto);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        kafkaTemplate.send(kafkaTopic, jsonInString);
//        log.info("Kafka Producer send data from the Order microservice: {}", orderDto);
//
//        return orderDto;
//    }
//
//}
