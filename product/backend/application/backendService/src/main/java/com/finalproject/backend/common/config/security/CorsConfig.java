package com.finalproject.backend.common.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to set up CORS mappings.
 */
@Configuration
@SuppressWarnings("PMD.AtLeastOneConstructor")
public class CorsConfig implements WebMvcConfigurer {

  /**
   * Configures CORS mappings.
   *
   * @param registry the {@link CorsRegistry} to configure
   */
  @Override
  public final void addCorsMappings(final CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOriginPatterns("*")
            .allowedMethods("GET", "POST", "PUT", "OPTIONS")
            .maxAge(-1)
            .allowedHeaders("*")
            .allowCredentials(true);
  }
}
