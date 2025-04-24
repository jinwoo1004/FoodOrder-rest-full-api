package com.kr.foodorder.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi foodOrderApi() {
        return GroupedOpenApi.builder()
                .group("food-order-api")
                .packagesToScan("com.kr.foodorder.controller")
                .build();
    }
}