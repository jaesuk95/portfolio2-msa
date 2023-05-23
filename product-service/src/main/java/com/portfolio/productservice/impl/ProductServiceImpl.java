package com.portfolio.productservice.impl;

import com.portfolio.productservice.controller.response.ResponseProduct;
import com.portfolio.productservice.model.product.ProductEntity;
import com.portfolio.productservice.model.product.ProductRepository;
import com.portfolio.productservice.model.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    @Override
    public ResponseProduct getProduct(String productId) {
        ResponseProduct responseProduct = new ResponseProduct();
        ProductEntity productEntity = productRepository.findByProductId(productId);

        if (productEntity.getStock() <= 0) {
            responseProduct.setMessage("Product's stock is empty");
            responseProduct.setStatus(400);
            return responseProduct;
        }

        responseProduct.setProductId(productEntity.getProductId());
        responseProduct.setPrice(productEntity.getPrice());
        responseProduct.setStatus(200);
        responseProduct.setMessage("Available");
        return responseProduct;
    }
}
