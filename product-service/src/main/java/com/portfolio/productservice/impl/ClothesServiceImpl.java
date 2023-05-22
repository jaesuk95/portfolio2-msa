package com.portfolio.productservice.impl;

import com.portfolio.productservice.controller.response.ResponseClothes;
import com.portfolio.productservice.feign.UserServiceClient;
import com.portfolio.productservice.model.product.clothes.*;
import com.portfolio.productservice.model.product.clothes.dto.ClothesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClothesServiceImpl implements ClothesService {

    private final ClothesRepository clothesRepository;
    private final ClothesQueryDslRepository clothesQueryDslRepository;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final UserServiceClient userServiceClient;

    @Override
    public ClothesDto registerClothes(ClothesDto clothesDto) {

        String userId = clothesDto.getUserId();
        // 제품을 등록하기 전, 사용자를 확인한다.
        log.info("Before call orders microservice");
        CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitBreaker");
        circuitbreaker.run(() -> userServiceClient.checkOwner(userId),
                throwable -> new ArrayList<>());    // <- throwable -> new ArrayList<>() 이 코드의 뜻은, orderServiceClient.getOrders(id) 에서 오류가 발생하면 비어있는 arrayList[] 으로 반환한다는 뜻이다.
        log.info("After called orders microservice");

        Clothes clothes = new Clothes(
                clothesDto.getStock(),
                LocalDateTime.now(),
                clothesDto.getClothesType(),
                clothesDto.getLengthType(),
                clothesDto.getPrice(),
                clothesDto.getUserId(),
                companyId
        );

        clothesRepository.save(clothes);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(clothes, ClothesDto.class);
    }

    @Override
    public Page<ResponseClothes> viewAllClothes(Pageable pageable) {
        Page<ClothesDto> clothesDto = clothesQueryDslRepository.getClothes(pageable);

        List<ResponseClothes> responseList = new ArrayList<>();

        clothesDto.forEach(v -> {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            ResponseClothes responseClothesMapped = modelMapper.map(v, ResponseClothes.class);
            responseList.add(responseClothesMapped);
        });

        PageImpl<ResponseClothes> responseClothesPage = new PageImpl<>(responseList, clothesDto.getPageable(), clothesDto.getTotalPages());
        return responseClothesPage;
    }


}
