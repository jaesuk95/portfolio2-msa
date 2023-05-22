package com.portfolio.productservice.impl;

import com.portfolio.productservice.controller.response.ResponseClothes;
import com.portfolio.productservice.model.product.clothes.*;
import com.portfolio.productservice.model.product.clothes.dto.ClothesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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

    @Override
    public ClothesDto registerClothes(ClothesDto clothesDto) {

        Clothes clothes = new Clothes(
                clothesDto.getBrandName(),
                clothesDto.getCompanyRegistered(),
                clothesDto.getStock(),
                LocalDateTime.now(),
                clothesDto.getClothesType(),
                clothesDto.getLengthType(),
                clothesDto.getPrice()
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
