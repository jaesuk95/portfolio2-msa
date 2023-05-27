package com.portfolio.productservice.impl;

import com.portfolio.productservice.controller.response.ResponseClothes;
import com.portfolio.productservice.controller.response.ResponseCompany;
import com.portfolio.productservice.feign.UserServiceClient;
import com.portfolio.productservice.message.dto.clothes.KafkaClothesDto;
import com.portfolio.productservice.message.dto.product.KafkaProductDto;
import com.portfolio.productservice.message.kafka.producer.KafkaClothesProducer;
import com.portfolio.productservice.message.kafka.producer.KafkaProductProducer;
import com.portfolio.productservice.model.product.ProductEntity;
import com.portfolio.productservice.model.product.ProductRepository;
import com.portfolio.productservice.model.product.clothes.*;
import com.portfolio.productservice.model.product.clothes.dto.ProductClothesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClothesServiceImpl implements ClothesService {

    private final ClothesRepository clothesRepository;
    private final ProductRepository productRepository;
    private final ClothesQueryDslRepository clothesQueryDslRepository;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final UserServiceClient userServiceClient;
    private final KafkaClothesProducer kafkaClothesProducer;
    private final KafkaProductProducer kafkaProductProducer;
    private final KafkaTemplate<String, String> kafkaTemplate;


    @Override
    public ResponseClothes registerClothes(ProductClothesDto productClothesDto) {
        String userId = productClothesDto.getUser_id();

        // 제품을 등록하기 전, 사용자를 확인한다.
        log.info("Before call orders microservice");
        CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitBreaker");
        List<ResponseCompany> responseCompanies = circuitbreaker.run(() -> userServiceClient.companyValidation(userId),
                throwable -> new ArrayList<>());// <- throwable -> new ArrayList<>() 이 코드의 뜻은, orderServiceClient.getOrders(id) 에서 오류가 발생하면 비어있는 arrayList[] 으로 반환한다는 뜻이다.
        log.info("After called orders microservice");

        Optional<ResponseCompany> responseCompany = responseCompanies.stream()
                .filter(data -> data.getCompanyName().equals(productClothesDto.getCompanyName()))
                .findFirst();
        if (responseCompany.isEmpty()) {
            throw new IllegalStateException("Company not found, not eligible to register");
        }
        log.info("found company, user is eligible to register a product");
        productClothesDto.setProductId(UUID.randomUUID().toString());

        KafkaProductDto kafkaProductDto = new KafkaProductDto(
                productClothesDto.getStock(),
                productClothesDto.getPrice(),
                productClothesDto.getUser_id(),
                productClothesDto.getCompanyName(),
                productClothesDto.getProductId(),
                "PRODUCT_CLOTHES"
        );

        kafkaProductProducer.send("product", kafkaProductDto);

        ProductEntity productEntity = productRepository.findByProductId(productClothesDto.getProductId());

        KafkaClothesDto kafkaClothesDto = KafkaClothesDto.builder().build();
        kafkaClothesDto.setClothesType(productClothesDto.getClothesType().toString());
        kafkaClothesDto.setLengthType(productClothesDto.getLengthType().toString());
        kafkaClothesDto.setProduct_entity_id(productEntity.getId());

        kafkaClothesProducer.send("clothes", kafkaClothesDto);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(productClothesDto, ResponseClothes.class);
    }

    @Override
    public Page<ResponseClothes> viewAllClothes(Pageable pageable) {
        Page<ProductClothesDto> clothesDto = clothesQueryDslRepository.getClothes(pageable);

        List<ResponseClothes> responseList = new ArrayList<>();

        clothesDto.forEach(v -> {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            ResponseClothes responseClothesMapped = modelMapper.map(v, ResponseClothes.class);
            responseList.add(responseClothesMapped);
        });

        return new PageImpl<>(responseList, clothesDto.getPageable(), clothesDto.getTotalPages());
    }

}
