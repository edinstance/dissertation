package com.finalproject.backend.config.security;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This class contains the security config for the test profile.
 */
@Configuration
@EnableWebSecurity
@Profile("test")
public class TestSecurityConfig {

  /**
   * This is api key for the application.
   * It is retrieved from the application.yml file using @Value.
   */
  @Value("${api.key}")
  private String apiKey;

  /**
   * Configures the security filter chain for the application.
   *
   * <p>This method sets up the security configuration, including CORS settings
   * and request authorization.</p>
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
                    // The healthcheck requests need
                    // authentication so that the authenticated
                    // routes can be tested
                    .requestMatchers("/details/health").authenticated()
                    // All other requests are permitted
                    .anyRequest().permitAll()
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
