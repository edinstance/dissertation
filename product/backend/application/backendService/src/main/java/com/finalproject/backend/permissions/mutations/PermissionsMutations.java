package com.finalproject.backend.permissions.mutations;

import com.finalproject.backend.common.dto.MutationResponse;
import com.finalproject.backend.permissions.dto.CreatePermissionInput;
import com.finalproject.backend.permissions.services.PermissionsService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class contains the mutations for permissions.
 */
@DgsComponent
public class PermissionsMutations {

  /**
   * The service for managing permissions.
   */
  private final PermissionsService permissionsService;

  /**
   * Constructs a PermissionMutations with the specified PermissionsService.
   *
   * @param permissionsService The service for managing permissions.
   */
  @Autowired
  public PermissionsMutations(PermissionsService permissionsService) {
    this.permissionsService = permissionsService;
  }

  /**
   * This query creates a new permission and the resource and
   * action associated with it if they do not exist.
   *
   * @param input the input for creating a permission.
   * @return a response about the success of the operation.
   */
  @DgsMutation
  public MutationResponse createPermission(
          @InputArgument final @NotNull CreatePermissionInput input) {

    boolean result = permissionsService.createPermission(
            input.getPermissionDescription(),
            input.getAction(),
            input.getActionDescription(),
            input.getResource(),
            input.getResourceDescription()
    );

    if (result) {
      return new MutationResponse(true, "Permission created successfully");
    }
    return new MutationResponse(false, "Error creating permission");
  }
}
