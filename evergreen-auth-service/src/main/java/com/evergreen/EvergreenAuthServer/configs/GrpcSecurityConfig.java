package com.evergreen.EvergreenAuthServer.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;

@Configuration
public class GrpcSecurityConfig {

    @Bean
    public GrpcAuthenticationReader grpcAuthenticationReader() {
        // Simple implementation that ignores authentication for now
        return (call, headers) -> null;
    }
}
