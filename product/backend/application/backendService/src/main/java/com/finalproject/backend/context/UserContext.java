package com.finalproject.backend.context;

import lombok.Getter;

/**
 * Custom context for the user.
 */
@Getter
public class UserContext {

  private final String userId;

  /**
   * Constructs a UserContext with the specified user ID.
   *
   * @param inputUserId The user ID to be stored in the context.
   */
  public UserContext(final String inputUserId) {
    this.userId = inputUserId;
  }
}