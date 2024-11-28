package com.finalproject.backend.mutations;

import com.finalproject.backend.dto.UserDetailsInput;
import com.finalproject.backend.entities.UserDetailsEntity;
import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.mappers.UserDetailsMapper;
import com.finalproject.backend.services.UserDetailsService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import java.util.UUID;

/**
 * GraphQL mutations for user-details-related operations.
 */
@DgsComponent
public class UserDetailsMutations {

  /**
   * The service the mutation will interact with.
   */
  private final UserDetailsService userService;

  /**
   * The mapper used to map from user details inputs to user entities.
   */
  private final UserDetailsMapper userDetailsMapper;

  /**
   * Constructor for initialising the UserMutations with the input services.
   *
   * @param inputUserService       The user service to be used by this component.
   * @param inputUserDetailsMapper The user mapper to be used by this component.
   */
  public UserDetailsMutations(final UserDetailsService inputUserService,
                              final UserDetailsMapper inputUserDetailsMapper) {
    this.userService = inputUserService;
    this.userDetailsMapper = inputUserDetailsMapper;
  }

  /**
   * GraphQL mutation to create or update user details.
   *
   * @param detailsInput The input data for the user details.
   * @return The created UserEntity.
   */
  @DgsMutation
  public UserEntity saveUserDetails(
          @InputArgument final String id,
          @InputArgument UserDetailsInput detailsInput) {
    UserDetailsEntity userDetails = userDetailsMapper.mapInputToDetails(UUID.fromString(id),
            detailsInput);
    return userService.saveUserDetails(userDetails);
  }
}
