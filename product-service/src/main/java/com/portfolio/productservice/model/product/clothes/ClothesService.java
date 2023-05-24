package com.portfolio.productservice.model.product.clothes;

import com.portfolio.productservice.controller.response.ResponseClothes;
import com.portfolio.productservice.model.product.clothes.dto.ProductClothesDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClothesService {
    ResponseClothes registerClothes(ProductClothesDto requestClothes);

    Page<ResponseClothes> viewAllClothes(Pageable pageable);

}
