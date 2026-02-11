package com.evergreen.EvergreenPaymentServer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.evergreen.EvergreenPaymentServer.filters.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(exceptionResolver);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(Customizer.withDefaults()).csrf((csrf) -> csrf.disable()).httpBasic((basic) -> basic.disable())
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll().requestMatchers("/api/v1/**").authenticated())
                // .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    // @Bean
    // public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    // return config.getAuthenticationManager();
    // }

    // @Bean
    // public AuthenticationManager
    // authenticationManager(AuthenticationConfiguration
    // authenticationConfiguration) throws Exception {
    // AuthenticationManager authenticationManager =
    // authenticationConfiguration.getAuthenticationManager();
    // return authenticationManager;
    // }

    // @Bean
    // public BCryptPasswordEncoder bCryptPasswordEncoder() {
    // return new BCryptPasswordEncoder();
    // }

    // @Bean
    // public AuthenticationProvider authenticationProvider() {
    // DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(appUserDetailsService);
    // daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
    // return daoAuthenticationProvider;
    // }
}
