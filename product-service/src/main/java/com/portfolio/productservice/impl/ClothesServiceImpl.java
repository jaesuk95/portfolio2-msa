package com.portfolio.productservice.impl;

import com.portfolio.productservice.model.product.clothes.*;
import com.portfolio.productservice.model.product.clothes.dto.ClothesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClothesServiceImpl implements ClothesService {

    private final ClothesRepository clothesRepository;

    @Override
    public ClothesDto registerClothes(ClothesDto clothesDto) {

        Clothes clothes = new Clothes(
                clothesDto.getBrandName(),
                clothesDto.getCompanyRegistered(),
                clothesDto.getStock(),
                LocalDateTime.now(),
                ClothesType.valueOf(clothesDto.getClothesType()),
                LengthType.valueOf(clothesDto.getLengthType()),
                clothesDto.getPrice()
        );

        clothesRepository.save(clothes);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(clothes, ClothesDto.class);
    }

    @Override
    public List<ClothesDto> viewAllClothes(Pageable pageable) {
//        clothesQueryDslRepository.getClothes(pageable);
        List<ClothesDto> objects = new ArrayList<>();
        return objects;
    }

    private final ClothesQueryDslRepository clothesQueryDslRepository;
}
