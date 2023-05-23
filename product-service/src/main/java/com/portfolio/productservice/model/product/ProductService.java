package com.portfolio.productservice.model.product;

import com.portfolio.productservice.controller.response.ResponseProduct;

public interface ProductService {
    ResponseProduct getProduct(String productId);
}
