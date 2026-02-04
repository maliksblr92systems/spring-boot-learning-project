package com.evergreen.gateway.configs;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

        @Bean
        public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
                return routeLocatorBuilder.routes()
                                .route((r) -> r.path("/orechestrator/**").filters((f) -> f.prefixPath("/api/v1").addResponseHeader("X-POWERED-BY", "Evergreen Gateway")).uri("http://localhost:8080"))

                                // payment-service

                                .route((r) -> r.path("/api/v1//payment/**").filters((f) -> f.prefixPath("").addResponseHeader("X-POWERED-BY", "Evergreen Gateway")).uri("http://localhost:8081"))
                                // auth-service

                                .route((r) -> r.path("/api/v1//auth/**").filters((f) -> f.prefixPath("").addResponseHeader("X-POWERED-BY", "Evergreen Gateway")).uri("http://localhost:8082")).build();

        }

}
