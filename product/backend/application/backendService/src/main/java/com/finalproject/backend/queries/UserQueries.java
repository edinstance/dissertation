package com.finalproject.backend.queries;

import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.helpers.AuthHelpers;
import com.finalproject.backend.helpers.UserHelpers;
import com.finalproject.backend.services.UserService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

/**
 * This class contains the user queries.
 */
@DgsComponent
public class UserQueries {

  /**
   * The service the query will interact with.
   */
  private final UserService userService;

  /**
   * The helper the query will use.
   */
  private final AuthHelpers authHelpers;

  /**
   * Constructs a UserQueries instance with the specified UserService.
   *
   * @param userService The service to interact with.
   * @param authHelpers the helper to interact with.
   */
  public UserQueries(UserService userService, AuthHelpers authHelpers) {
    this.userService = userService;
    this.authHelpers = authHelpers;
  }

  /**
   * This query returns a user based on their ID token.
   *
   * @return The user.
   */
  @DgsQuery
  public UserEntity getUser() {
    return userService.getUserById(authHelpers.getCurrentUserId());
  }
}