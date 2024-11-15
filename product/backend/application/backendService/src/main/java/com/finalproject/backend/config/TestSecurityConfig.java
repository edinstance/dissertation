package com.finalproject.backend.config;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This is the security config for testing.
 */
@Configuration
@EnableWebSecurity
@Profile("test")
public class TestSecurityConfig {


    @Value("${api.key}")
    private String apiKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        Filter apiKeyFilter = new ApiKeyFilter(apiKey);
        http
                .cors(httpSecurityCorsConfigurer -> Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/details/health").authenticated()
                        .anyRequest().permitAll() // All requests are permitted
                )
                .addFilterBefore(apiKeyFilter, BearerTokenAuthenticationFilter.class) // Add ApiKeyFilter before BearerTokenAuthenticationFilter
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}