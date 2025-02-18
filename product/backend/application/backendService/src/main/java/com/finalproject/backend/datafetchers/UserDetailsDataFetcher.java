package com.finalproject.backend.datafetchers;

import com.finalproject.backend.config.logging.AppLogger;
import com.finalproject.backend.entities.UserDetailsEntity;
import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.helpers.UserHelpers;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Data fetcher for user-related GraphQL queries.
 */
@DgsComponent
public class UserDetailsDataFetcher {

  /**
   * The UserHelpers to be used by this data fetcher.
   */
  private final UserHelpers userHelpers;

  /**
   * Constructs a UserDataFetcher with the specified UserHelpers.
   *
   * @param userHelpers The UserHelpers to be used by this data fetcher.
   */
  @Autowired
  public UserDetailsDataFetcher(final UserHelpers userHelpers) {
    this.userHelpers = userHelpers;
  }

  /**
   * Retrieves the user details for a user.
   *
   * @param dfe The data fetching environment.
   * @return The user details.
   */
  @DgsData(parentType = "User", field = "details")
  public UserDetailsEntity getUserDetails(@NotNull DgsDataFetchingEnvironment dfe) {
    UserEntity user = dfe.getSource();
    
    if (user == null) {
      AppLogger.error("UserDetailsDataFetcher.getUserDetails() returned null");
      throw new IllegalArgumentException("User not found");
    }
    return userHelpers.getUserById(user.getId()).getUserDetailsEntity();
  }
}