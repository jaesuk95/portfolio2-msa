package com.portfolio.productservice.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseClothes {
    private String brandName;
    private String companyRegistered;
    private Integer stock;
    private LocalDateTime registeredDate;
    private String lengthType;
    private String clothesType;
    private int price;
}
