package backend.common.helpers;

import backend.common.config.logging.AppLogger;
import java.util.Collections;
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
    try {
      AppLogger.info("Getting current user groups",
              SecurityContextHolder.getContext().getAuthentication().getPrincipal());

      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if (principal instanceof Jwt jwt) {
        return jwt.getClaimAsStringList("cognito:groups");
      }
      AppLogger.warn("Unable to extract user groups from token: Principal is not a JWT");
      return Collections.emptyList();
    } catch (Exception e) {
      AppLogger.error("Error extracting user groups: " + e.getMessage(), e);
      return Collections.emptyList();
    }
  }
}
