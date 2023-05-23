package com.portfolio.productservice.message.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.productservice.message.dto.Field;
import com.portfolio.productservice.message.dto.KafkaClothesDto;
import com.portfolio.productservice.message.dto.Payload;
import com.portfolio.productservice.message.dto.Schema;
import com.portfolio.productservice.model.product.clothes.dto.ClothesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaClothesProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fields = Arrays.asList(
            new Field("enum", true, "clothes_type"),
            new Field("enum", true, "length_type"),
            new Field("int32", true, "stock"),
            new Field("datetime", true, "registered_date"),
            new Field("string", true, "price"),
            new Field("string", true, "user_id"),
            new Field("string", true, "company_name"));
    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("clothes")
            .build();

    public ClothesDto send(String kafkaTopic, ClothesDto clothesDto) {
        // create payload
        Payload payload = Payload.builder()
                .clothes_type(clothesDto.getClothesType())
                .length_type(clothesDto.getLengthType())
                .stock(clothesDto.getStock())
                .registered_date(clothesDto.getRegisteredDate())
                .price(clothesDto.getPrice())
                .user_id(clothesDto.getUser_id())
                .company_name(clothesDto.getCompanyName())
                .build();

//                .order_id(orderDto.getOrderId())
//                .user_id(orderDto.getUserId())
//                .product_id(orderDto.getProductId())
//                .qty(orderDto.getQty())
//                .unit_price(orderDto.getUnitPrice())
//                .total_price(orderDto.getTotalPrice())
//                .build();

        KafkaClothesDto kafkaClothesDto = new KafkaClothesDto(schema,payload);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(kafkaClothesDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(kafkaTopic, jsonInString);
        log.info("Kafka Producer send data from the Order microservice: {}", clothesDto);

        return clothesDto;
    }
}
