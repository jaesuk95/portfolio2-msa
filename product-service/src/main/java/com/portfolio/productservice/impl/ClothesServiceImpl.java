package com.portfolio.productservice.impl;

import com.portfolio.productservice.controller.request.RequestClothes;
import com.portfolio.productservice.model.product.clothes.ClothesEntity;
import com.portfolio.productservice.model.product.clothes.ClothesRepository;
import com.portfolio.productservice.model.product.clothes.ClothesService;
import com.portfolio.productservice.model.product.clothes.dto.ClothesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClothesServiceImpl implements ClothesService {

    private final ClothesRepository clothesRepository;

    @Override
    public ClothesDto registerClothes(RequestClothes requestClothes) {
        ClothesDto clothesDto = ClothesDto.builder()
                .stock(requestClothes.getStock())
                .registeredDate(LocalDateTime.now())
                .clothesType(requestClothes.getClothesType())
                .companyRegistered(requestClothes.getCompanyRegistered())
                .brandName(requestClothes.getBrandName())
                .lengthType(requestClothes.getLengthType())
                .build();

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ClothesEntity clothesEntity = mapper.map(clothesDto, ClothesEntity.class);

        clothesRepository.save(clothesEntity);
        return clothesDto;
    }
}
