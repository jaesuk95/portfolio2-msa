package com.portfolio.productservice.model.product.clothes;

import com.portfolio.productservice.controller.request.RequestClothes;
import com.portfolio.productservice.model.product.clothes.dto.ClothesDto;

public interface ClothesService {
    ClothesDto registerClothes(RequestClothes requestClothes);
}
