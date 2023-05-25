package com.portfolio.productservice.message.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.productservice.message.dto.Field;
import com.portfolio.productservice.message.dto.product.KafkaProducerProduct;
import com.portfolio.productservice.message.dto.product.KafkaProductDto;
import com.portfolio.productservice.message.dto.product.Payload;
import com.portfolio.productservice.message.dto.Schema;
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
public class KafkaProductProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fields = Arrays.asList(
            new Field("int32", true, "stock"),
            new Field("int32", true, "price"),
            new Field("string", true, "user_id"),
            new Field("string", true, "company_name"),
            new Field("string", true, "product_id"),
            new Field("string", true, "type")
    );

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("product")
            .build();


    public void send(String kafkaTopic, KafkaProductDto productDto) {
        // create payload
        Payload payload = Payload.builder()
                .stock(productDto.getStock())
                .price(productDto.getPrice())
                .user_id(productDto.getUserId())
                .company_name(productDto.getCompanyName())
                .product_id(productDto.getProductId())
                .type(productDto.getType())
                .build();

        KafkaProducerProduct kafkaProducerProduct = new KafkaProducerProduct(schema, payload);

        String jsonInString = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonInString = objectMapper.writeValueAsString(kafkaProducerProduct);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        kafkaTemplate.send(kafkaTopic, jsonInString);

        // ListenableFutureCallback is added to handle success and failure cases asynchronously.
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(kafkaTopic, jsonInString);

        future.whenComplete((result, exception) -> {
            if (exception == null) {
                // Message was successfully sent
                // Handle the SendResult if needed
                log.info("Message has been successfully sent");
                future.isDone();
            } else {
                // Error occurred during message sending
                // Handle the exception if needed
                log.error("Message denied from Product registration");
                future.completeExceptionally(exception);
            }
        });

    }
}
