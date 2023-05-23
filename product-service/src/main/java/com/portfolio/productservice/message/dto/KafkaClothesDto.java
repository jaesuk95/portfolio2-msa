package com.portfolio.productservice.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class KafkaClothesDto implements Serializable {
    private Schema schema;
    private Payload payload;
}
