package com.portfolio.productservice.message.dto.clothes;

import com.portfolio.productservice.message.dto.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class KafkaProducerClothes implements Serializable {
    private Schema schema;
    private Payload payload;
}
