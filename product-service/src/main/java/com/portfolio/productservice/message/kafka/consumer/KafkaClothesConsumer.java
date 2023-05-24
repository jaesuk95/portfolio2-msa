//package com.portfolio.productservice.message.kafka.consumer;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.portfolio.productservice.message.dto.Payload;
//import com.portfolio.productservice.model.product.clothes.Clothes;
//import com.portfolio.productservice.model.product.clothes.ClothesRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.convention.MatchingStrategies;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//import com.fasterxml.jackson.core.type.TypeReference;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class KafkaClothesConsumer {
//
//    private final ClothesRepository clothesRepository;
//
//    @KafkaListener(topics = "clothes")
//    public void processMessage(String kafkaMessage) {
//        log.info("Kafka message: => {}", kafkaMessage);
//
//        Map<Object, Object> map = new HashMap<>();
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        Payload payload = modelMapper.map(map.get("payload"), Payload.class);
//
//        Clothes clothes = new Clothes(
//                payload.getStock(),
//                LocalDateTime.now(),
//                payload.getClothes_type(),
//                payload.getLength_type(),
//                payload.getPrice(),
//                payload.getUser_id(),
//                payload.getCompany_name(),
//                payload.getProduct_id()
//        );
//
//        clothesRepository.save(clothes);
//        log.info("@KafkaListener -> new data has been saved under ClothesEntity");
//    }
//}
