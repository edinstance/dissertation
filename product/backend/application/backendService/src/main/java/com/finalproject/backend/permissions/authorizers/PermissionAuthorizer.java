package com.finalproject.backend.permissions.authorizers;

import com.finalproject.backend.permissions.types.*;
import com.finalproject.backend.permissions.utils.PermissionKey;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This interface is responsible for checking if a user has permissions.
 */
public interface PermissionAuthorizer<T extends PermissionView> {

  /**
   * Checks if a user has permission to access a resource.
   *
   * @param userId the user id.
   * @param resource the resource.
   * @param action the action.
   * @param grantType the grant type.
   * @param viewType the view type.
   * @return {@code true} if the user has permission, {@code false} otherwise.
   */
  boolean authorize(UUID userId, Resources resource, Actions action,
                    GrantType grantType, ViewTypes viewType);


  /**
   * This function gets the effective permissions from a list of user.
   * It ensures that if there is a DENY permission, it will override any GRANT permissions.
   *
   * @param permissions the list of permissions.
   * @return the new list of effective permissions.
   */
  default List<T> getEffectivePermissions(List<T> permissions) {
    // First, collect the keys for all DENY permissions.
    Set<PermissionKey> deniedPermissions = permissions.stream()
            .filter(p -> p.getGrantType().equals(GrantType.DENY))
            .map(p -> new PermissionKey(p.getAssociatedUserId(), p.getResource(), p.getAction()))
            .collect(Collectors.toSet());

    // Then, return only GRANT permissions that are not denied.
    return permissions.stream()
            .filter(p -> p.getGrantType().equals(GrantType.GRANT))
            .filter(p -> !deniedPermissions.contains(
                    new PermissionKey(p.getAssociatedUserId(), p.getResource(), p.getAction())))
            .collect(Collectors.toList());
  }
}
