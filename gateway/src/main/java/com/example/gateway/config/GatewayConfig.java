package com.example.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("role-service", r -> r
                        .path("/api/employee-roles/**")
                        .uri("http://role-service:8081"))

                .route("employee-service", r -> r
                        .path("/api/employees/**")
                        .uri("http://employee-service:8082"))

                .build();
    }
}