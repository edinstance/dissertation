package com.finalproject.backend.common.helpers;

import com.finalproject.backend.common.config.logging.AppLogger;

import java.util.List;
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

  /**
   * Extracts the users groups from the JWT token.
   *
   * @return A list of the users groups.
   * @throws IllegalStateException if the groups are missing or invalid.
   */
  public List<String> getCurrentUserGroups() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof Jwt jwt) {
      return jwt.getClaimAsStringList("cognito:groups");
    }
    AppLogger.error("Unable to extract user groups from token");
    throw new IllegalStateException("Unable to extract user groups from token");
  }
}
