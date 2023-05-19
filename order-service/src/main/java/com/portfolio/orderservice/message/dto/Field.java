package com.portfolio.orderservice.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Field {
    private String type;
    private boolean optional;
    private String field;
}
