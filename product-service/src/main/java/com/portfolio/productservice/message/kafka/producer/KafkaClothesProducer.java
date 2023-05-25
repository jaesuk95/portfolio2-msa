package com.portfolio.productservice.message.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.productservice.message.dto.*;
import com.portfolio.productservice.message.dto.clothes.KafkaClothesDto;
import com.portfolio.productservice.message.dto.clothes.KafkaProducerClothes;
import com.portfolio.productservice.message.dto.clothes.Payload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaClothesProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fields = Arrays.asList(
            new Field("string", true, "clothes_type"),
            new Field("string", true, "length_type"),
            new Field("int64", true, "product_entity_id")
    );

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("clothes")
            .build();


    public void send(String kafkaTopic, KafkaClothesDto kafkaClothesDto) {
        // create payload
        Payload payload = Payload.builder()
                .clothes_type(kafkaClothesDto.getClothesType())
                .length_type(kafkaClothesDto.getLengthType())
                .product_entity_id(kafkaClothesDto.getProduct_entity_id())
                .build();

        KafkaProducerClothes kafkaProducerClothes = new KafkaProducerClothes(schema, payload);

        String jsonInString = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonInString = objectMapper.writeValueAsString(kafkaProducerClothes);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(kafkaTopic, jsonInString);
        log.info("KAFKA 'clothes' message has been successfully sent");
    }
}
