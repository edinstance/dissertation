package com.finalproject.backend.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;

/**
 * This class sets up a jwt decoder.
 */
@Configuration
public class JwtDecoderConfig {

  /**
   * This is the issuer uri for the jwt tokens.
   * It is retrieved from the application.yml file using @Value.
   */
  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String jwtIssuerUri;

  /**
   * Creates a {@link JwtDecoderConfig} bean to validate JWT tokens.
   *
   * @return the configured {@link JwtDecoderConfig}
   */
  @Bean
  public JwtDecoder jwtDecoder() {
    // Use the issuer-uri from cognito
    return JwtDecoders.fromIssuerLocation(jwtIssuerUri);
  }
}
