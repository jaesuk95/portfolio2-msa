package com.portfolio.productservice.message.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
//            new Field("string", true, "clothes_type"),
//            new Field("string", true, "length_type"),
            new Field("int32", true, "stock"),
            new Field("int32", true, "price"),
            new Field("string", true, "user_id"),
            new Field("string", true, "company_name"),
            new Field("string", true, "product_id"),
            new Field("string", true, "type")
//            new Field("int64", true, "id")
    );

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("clothes")
            .build();


    public void send(String kafkaTopic, ClothesDto clothesDto) {
        // create payload
        Payload payload = Payload.builder()
//                .clothes_type(clothesDto.getClothesType().toString())
//                .length_type(clothesDto.getLengthType().toString())
                .stock(clothesDto.getStock())
//                .registered_date(clothesDto.getRegisteredDate())
                .price(clothesDto.getPrice())
                .user_id(clothesDto.getUser_id())
                .company_name(clothesDto.getCompanyName())
                .product_id(clothesDto.getProductId())
                .type(clothesDto.getType())
//                .id(clothesDto.getId())
                .build();

        KafkaClothesDto kafkaClothesDto = new KafkaClothesDto(schema,payload);

//        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            jsonInString = objectMapper.writeValueAsString(kafkaClothesDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(kafkaTopic, jsonInString);
        log.info("Kafka Producer send data from the Order microservice: {}", clothesDto);
// {"schema":{"type":"struct","fields":[{"type":"int32","optional":true,"field":"stock"},{"type":"string","optional":true,"field":"price"},{"type":"string","optional":true,"field":"user_id"},{"type":"string","optional":true,"field":"company_name"},{"type":"string","optional":true,"field":"product_id"}],"optional":false,"name":"clothes"},"payload":{"stock":10,"price":1000,"user_id":"1","company_name":"companyName","product_id":"productId"}}
    }
}

// {"schema":{"type":"struct","fields":[{"type":"string","optional":true,"field":"clothes_type"},{"type":"string","optional":true,"field":"length_type"},{"type":"int32","optional":true,"field":"stock"},{"type":"string","optional":true,"field":"price"},{"type":"string","optional":true,"field":"user_id"},{"type":"string","optional":true,"field":"company_name"},{"type":"string","optional":true,"field":"product_id"},{"type":"int64","optional":true,"field":"id"}],"optional":false,"name":"clothes"},"payload":{"clothes_type":"shirt","length_type":"longLength","stock":10,"price":1000,"user_id":"1","company_name":"companyName","product_id":"productId","id":1}}