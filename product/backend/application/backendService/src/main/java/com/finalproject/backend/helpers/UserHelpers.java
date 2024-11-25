package com.finalproject.backend.helpers;

import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.services.UserService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;


/**
 * A set of helper functions for user interaction.
 */
@Component
public class UserHelpers {

  /**
   * The service the helpers interact with.
   */
  private final UserService userService;

  /**
   * Sets up the service.
   *
   * @param userService The service to interact with.
   */
  @Autowired
  public UserHelpers(UserService userService) {
    this.userService = userService;
  }

  /**
   * Retrieves a user entity by its ID.
   *
   * @param id The ID of the user.
   * @return The user entity.
   */
  public UserEntity getUserById(UUID id) {
    return userService.getUserById(id);
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
    throw new IllegalStateException("Unable to extract user ID from token");
  }

}