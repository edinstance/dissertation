package backend.users.queries;

import backend.users.services.UserDetailsService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

/**
 * This class contains the user details queries.
 */
@DgsComponent
public class UserDetailsQueries {

  /**
   * The service the query will interact with.
   */
  private final UserDetailsService userDetailsService;

  /**
   * Constructs a UserQueries instance with the specified UserService.
   *
   * @param userDetailsService The service to interact with.
   */
  public UserDetailsQueries(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  /**
   * This query returns whether a user has set their details.
   *
   * @return if the user has set details.
   */
  @DgsQuery
  public boolean checkCurrentUserDetailsExist() {
    return userDetailsService.checkCurrentUserDetailsExist();
  }
}