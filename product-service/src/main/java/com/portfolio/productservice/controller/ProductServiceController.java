package com.portfolio.productservice.controller;

import com.portfolio.productservice.controller.request.RequestClothes;
import com.portfolio.productservice.controller.response.ResponseClothes;
import com.portfolio.productservice.model.product.clothes.ClothesService;
import com.portfolio.productservice.model.product.clothes.dto.ClothesDto;
import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-service")
@RequiredArgsConstructor
@Slf4j
public class ProductServiceController {

    private final Environment env;
    private final ClothesService clothesService;

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
        ModelMapper mapper = new ModelMapper();
        ClothesDto clothesDto = mapper.map(requestClothes, ClothesDto.class);

        ClothesDto clothesDtoReturn = clothesService.registerClothes(clothesDto);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ResponseClothes returnValue = mapper.map(clothesDtoReturn, ResponseClothes.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping("/public/clothes/all")
    public ResponseEntity<Page<ResponseClothes>> viewAllClothes(
            Pageable pageable, HttpServletRequest request) {
        Page<ResponseClothes> responseClothes = clothesService.viewAllClothes(pageable);
        log.info("request for all clothes");
        return ResponseEntity.status(HttpStatus.OK).body(responseClothes);
    }
}
