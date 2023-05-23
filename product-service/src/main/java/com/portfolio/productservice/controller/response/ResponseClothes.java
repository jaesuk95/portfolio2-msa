package com.portfolio.productservice.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseClothes {
    private Long id;
    private String brandName;
    private String companyRegistered;
    private Integer stock;
    private LocalDateTime registeredDate;
    private String lengthType;
    private String clothesType;
    private Integer price;
    private String companyName;
    private String productId;

    private String errorMessage;

    public ResponseClothes(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
