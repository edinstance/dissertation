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

@Configuration
@EnableWebSecurity
@Profile("prod")
public class ProdSecurityConfig {

    // This gets the value from the application.yml file
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String JWTIssuerUri;

    @Value("${api.key}")
    private String apiKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        Filter apiKeyFilter = new ApiKeyFilter(apiKey);
        http
                .cors(httpSecurityCorsConfigurer -> Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow preflight requests to all endpoints
                        .anyRequest().authenticated() // All other requests must be authenticated
                )
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(Customizer.withDefaults()) // Validate JWT tokens by default
                )
                .addFilterBefore(apiKeyFilter, BearerTokenAuthenticationFilter.class) // Add ApiKeyFilter before BearerTokenAuthenticationFilter
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    // This bean is required for Spring Security to validate JWT tokens
    @Bean
    public JwtDecoder jwtDecoder() {
        // Use the issuer-uri from cognito
        return JwtDecoders.fromIssuerLocation(JWTIssuerUri);
    }
}