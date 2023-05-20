package com.portfolio.userservice.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class OrderServiceProperties {
    private String user_service_url;
    private String order_service_url;
    private String payment_service_url;
}
