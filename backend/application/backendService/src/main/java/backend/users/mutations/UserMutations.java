package backend.users.mutations;


import backend.common.config.logging.AppLogger;
import backend.common.dto.MutationResponse;
import backend.users.dto.UserInput;
import backend.users.entities.UserEntity;
import backend.users.mappers.UserMapper;
import backend.users.services.UserService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import java.util.UUID;


/**
 * GraphQL mutations for user-related operations.
 */
@DgsComponent
public class UserMutations {

  /**
   * The service the mutation will interact with.
   */
  private final UserService userService;

  /**
   * The mapper used to map from user inputs to user entities.
   */
  private final UserMapper userMapper;

  /**
   * Constructor for initializing the UserMutations with the input services.
   *
   * @param inputUserService The user service to be used by this component.
   * @param inputUserMapper  The user mapper to be used by this component.
   */
  public UserMutations(final UserService inputUserService,
                       final UserMapper inputUserMapper) {
    this.userService = inputUserService;
    this.userMapper = inputUserMapper;
  }

  /**
   * GraphQL mutation to create a new user.
   *
   * @param userInput The input data for the new user.
   *
   * @return The created UserEntity.
   */
  @DgsMutation
  public UserEntity createUser(@InputArgument final UserInput userInput) {
    UserEntity user = userService.createUser(userMapper.mapInputToUser(userInput));
    AppLogger.info("Created user: " + user);
    return user;
  }

  /**
   * GraphQL mutation to delete a user.
   *
   * @return a response about the success of the mutation.
   */
  @DgsMutation
  public MutationResponse deleteUser() {
    Boolean result = userService.deleteUser();

    if (result) {
      return new MutationResponse(true, "User deleted successfully");
    } else {
      return new MutationResponse(false, "User deletion failed");
    }
  }

  /**
   * GraphQL mutation to delete a user.
   *
   * @return a response about the success of the mutation.
   */
  @DgsMutation
  public MutationResponse deactivateUser(@InputArgument String id) {
    userService.deactivateUser(UUID.fromString(id));

    return new MutationResponse(true, "User deactivated successfully");
  }

}
