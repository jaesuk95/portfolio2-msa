package com.portfolio.productservice.controller;

import com.portfolio.productservice.controller.request.RequestClothes;
import com.portfolio.productservice.controller.response.ResponseClothes;
import com.portfolio.productservice.controller.response.ResponseProduct;
import com.portfolio.productservice.model.product.ProductService;
import com.portfolio.productservice.model.product.clothes.ClothesService;
import com.portfolio.productservice.model.product.clothes.dto.ProductClothesDto;
import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product-service")
@RequiredArgsConstructor
@Slf4j
public class ProductServiceController {

    private final Environment env;
    private final ClothesService clothesService;
    private final ProductService productService;

    @GetMapping("/health_check")
    @Timed(value = "users.status", longTask = true) // prometheus 에 등록
    public String status(HttpServletRequest request) {
        return String.format("It's working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", with token secret=" + env.getProperty("token.secret")
                + ", with token time=" + env.getProperty("token.expiration_time"));
    }

    @PostMapping("/clothes")
    public ResponseEntity<ResponseClothes> registerClothes(
            HttpServletRequest request, @RequestBody RequestClothes requestClothes) {
        try {
            ModelMapper mapper = new ModelMapper();
            ProductClothesDto productClothesDto = mapper.map(requestClothes, ProductClothesDto.class);

            ResponseClothes returnValue = clothesService.registerClothes(productClothesDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseClothes(e.getMessage()));
        }

    }

    @GetMapping("/public/clothes/all")
    public ResponseEntity<Page<ResponseClothes>> viewAllClothes(
            Pageable pageable, HttpServletRequest request) {
        Page<ResponseClothes> responseClothes = clothesService.viewAllClothes(pageable);
        log.info("request for all clothes");
        return ResponseEntity.status(HttpStatus.OK).body(responseClothes);
    }

    @GetMapping("/public/{productId}")
    public ResponseEntity<ResponseProduct> getProduct(@PathVariable String productId) {
        ResponseProduct responseProduct = productService.getProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(responseProduct);
    }
}
