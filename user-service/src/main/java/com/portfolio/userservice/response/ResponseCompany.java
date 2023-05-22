package com.portfolio.userservice.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCompany {
    private Long id;
    private String companyId;
    private LocalDateTime registerDate;
    private String companyName;
    private String brandName;
}
