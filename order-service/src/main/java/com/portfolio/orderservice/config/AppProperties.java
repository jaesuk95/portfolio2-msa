package com.portfolio.orderservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String user_service_url;
    private String order_service_url;
    private String payment_service_url;

    private String kafka_listener;
}
