package com.cts.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/user-api/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://user-service"))


                .route("driver-service", r -> r.path("/driver-api/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://driver-service"))

                .route("booking-service", r -> r.path("/booking-api/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://booking-service"))

                .route("payment-service", r -> r.path("/payment-api/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://payment-service"))

                .route("review-service", r -> r.path("/review-api/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://review-service"))

                .build();
    }
}



