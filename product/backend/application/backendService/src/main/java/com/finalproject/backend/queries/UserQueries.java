package com.finalproject.backend.queries;

import com.finalproject.backend.entities.UserEntity;
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
  private final UserHelpers userHelpers;

  /**
   * Constructs a UserQueries instance with the specified UserService.
   *
   * @param userService The service to interact with.
   * @param userHelpers the helper to interact with.
   */
  public UserQueries(UserService userService, UserHelpers userHelpers) {
    this.userService = userService;
    this.userHelpers = userHelpers;
  }

  /**
   * This query returns a user based on their ID token.
   *
   * @return The user.
   */
  @DgsQuery
  public UserEntity getUser() {
    return userService.getUserById(userHelpers.getCurrentUserId());
  }
}