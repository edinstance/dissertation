package com.finalproject.backend.context.builders;

import com.finalproject.backend.context.UserContext;
import com.netflix.graphql.dgs.context.DgsCustomContextBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * Custom context builder for creating UserContext instances.
 */
@Component
public class UserContextBuilder implements DgsCustomContextBuilder<UserContext> {

  /**
   * Builds a UserContext instance using the user ID extracted from the JWT.
   *
   * @return The created UserContext instance.
   */
  @Override
  public UserContext build() {
    Jwt jwt = (Jwt) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
    String userId = jwt.getClaimAsString("sub");
    return new UserContext(userId);
  }
}