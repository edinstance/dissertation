package com.finalproject.backend.common.config.security;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This class contains the security config for the prod profile.
 */
@Configuration
@EnableWebSecurity
@Profile("prod")
public class ProdSecurityConfig {

  /**
   * This is the issuer uri for the jwt tokens.
   * It is retrieved from the application.yml file using @Value.
   */
  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String jwtIssuerUri;

  /**
   * This is api key for the application.
   * It is retrieved from the application.yml file using @Value.
   */
  @Value("${api.key}")
  private String apiKey;

  /**
   * Configures the security filter chain for the application.
   *
   * <p>This method sets up the security configuration, including CORS settings,
   * request authorization, and JWT token validation.</p>
   *
   * @param http the {@link HttpSecurity} to modify
   * @return the configured {@link SecurityFilterChain}
   * @throws Exception if an error occurs while configuring the filter chain
   */
  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity http)
          throws Exception {
    Filter apiKeyFilter = new ApiKeyFilter(apiKey);
    http
            .cors(httpSecurityCorsConfigurer -> Customizer.withDefaults())
            .authorizeHttpRequests((authorize) -> authorize
                    // Allow preflight requests to all endpoints
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    // All other requests must be authenticated
                    .anyRequest().authenticated()
            )
            .oauth2ResourceServer((oauth2) -> oauth2
                    // Validate JWT tokens by default
                    .jwt(Customizer.withDefaults())
            )
            // Add ApiKeyFilter before BearerTokenAuthenticationFilter
            .addFilterBefore(apiKeyFilter,
                    BearerTokenAuthenticationFilter.class)
            .csrf(csrf -> csrf.disable());
    return http.build();
  }
}
