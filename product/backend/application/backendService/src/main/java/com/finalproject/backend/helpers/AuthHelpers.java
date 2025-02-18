package com.finalproject.backend.helpers;

import com.finalproject.backend.config.logging.AppLogger;
import java.util.UUID;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * A set of helpers for authentication.
 */
@Component
public class AuthHelpers {

  /**
   * Default constructor for the helpers.
   */
  public AuthHelpers() {
  }

  /**
   * Extracts the user ID (sub) from the JWT token.
   *
   * @return The user ID as a String.
   * @throws IllegalStateException if the token is missing or invalid.
   */
  public UUID getCurrentUserId() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof Jwt jwt) {
      return UUID.fromString(jwt.getClaimAsString("sub"));
    }
    AppLogger.error("Unable to extract user ID from token");
    throw new IllegalStateException("Unable to extract user ID from token");
  }
}
