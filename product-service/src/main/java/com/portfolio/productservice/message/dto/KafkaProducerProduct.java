package com.portfolio.productservice.message.dto;

import com.portfolio.productservice.message.dto.payload.ProductPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class KafkaProducerProduct implements Serializable {
    private Schema schema;
    private ProductPayload productPayload;
}
