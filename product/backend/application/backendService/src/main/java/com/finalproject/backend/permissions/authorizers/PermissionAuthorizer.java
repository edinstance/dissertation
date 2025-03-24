package com.finalproject.backend.permissions.authorizers;

import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.GrantType;
import com.finalproject.backend.permissions.types.Resources;
import com.finalproject.backend.permissions.types.ViewTypes;
import java.util.UUID;

/**
 * This interface is responsible for checking if a user has permissions.
 */
public interface PermissionAuthorizer {

  /**
   * This method checks if a user has permission to access a resource.
   *
   * @param userId the user id.
   * @param resource the resource.
   * @param action the action.
   * @param grantType the grant type.
   * @param viewType the view type.
   * @return if the user has permission.
   */
  boolean authorize(UUID userId, Resources resource,
                        Actions action, GrantType grantType, ViewTypes viewType);
}