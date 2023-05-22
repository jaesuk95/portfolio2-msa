package com.portfolio.productservice.model.product.clothes;

import com.portfolio.productservice.model.product.clothes.dto.ClothesDto;

import java.awt.print.Pageable;
import java.util.List;

public interface ClothesService {
    ClothesDto registerClothes(ClothesDto requestClothes);

    List<ClothesDto> viewAllClothes(Pageable pageable);
}
