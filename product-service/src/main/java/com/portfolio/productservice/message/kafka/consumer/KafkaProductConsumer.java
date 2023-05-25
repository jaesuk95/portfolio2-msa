package com.portfolio.productservice.message.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.productservice.message.dto.product.Payload;
import com.portfolio.productservice.model.product.ProductEntity;
import com.portfolio.productservice.model.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProductConsumer {

    private final ProductRepository productRepository;

    @KafkaListener(topics = "product_purchase_update")
    public void processProductUpdate(String kafkaMessage) {
        log.info("Kafka message: => {}", kafkaMessage);

        // from order-service
        //     List<Field> fields = Arrays.asList(
        //            new Field("string", true, "product_id"),
        //            new Field("int32", true, "qty"));

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Payload payload = modelMapper.map(map.get("payload"), Payload.class);

        ProductEntity productEntity = productRepository.findByProductId(payload.getProduct_id());
        productEntity.purchaseUpdate(payload.getStock());

        productRepository.save(productEntity);
        log.info("@KafkaListener -> new data has been saved under ProductEntity");
    }

}


