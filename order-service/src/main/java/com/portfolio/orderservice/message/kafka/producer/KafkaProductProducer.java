package com.portfolio.orderservice.message.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.orderservice.message.dto.Field;
import com.portfolio.orderservice.message.dto.KafkaOrderDto;
import com.portfolio.orderservice.message.dto.Payload;
import com.portfolio.orderservice.message.dto.Schema;
import com.portfolio.orderservice.model.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProductProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fields = Arrays.asList(
            new Field("string", true, "product_id"),
            new Field("int32", true, "qty"));

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("product_purchase_update")
            .build();

    public OrderDto send(String kafkaTopic, OrderDto orderDto) {
        // create payload
        Payload payload = Payload.builder()
                .product_id(orderDto.getProductId())
                .qty(orderDto.getQty())
                .build();

        KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema,payload);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(kafkaOrderDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(kafkaTopic, jsonInString);
        log.info("Kafka Producer send data from the Order microservice: {}", orderDto);

        return orderDto;
    }
}
