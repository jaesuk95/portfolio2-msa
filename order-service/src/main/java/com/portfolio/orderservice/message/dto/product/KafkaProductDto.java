package com.portfolio.orderservice.message.dto.product;

import com.portfolio.orderservice.message.dto.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class KafkaProductDto implements Serializable {
    private Schema schema;
    private Payload payload;
}
