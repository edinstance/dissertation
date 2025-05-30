package backend.users.helpers;

import backend.users.entities.UserEntity;
import backend.users.services.UserService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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
   *
   * @return The user entity.
   */
  public UserEntity getUserById(UUID id) {
    return userService.getUserById(id);
  }

}