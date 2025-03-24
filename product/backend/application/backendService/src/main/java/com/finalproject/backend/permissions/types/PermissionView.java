package com.finalproject.backend.permissions.types;

import java.util.UUID;

/**
 * This interface is for the different permission views.
 */
public interface PermissionView {

  /**
   * A function to get the user id associated with the permission.
   *
   * @return the id.
   */
  UUID getAssociatedUserId();

  /**
   *  A function to get the resource of the view.
   *
   * @return the resource.
   */
  Resources getResource();

  /**
   * A function to get the action of the view.
   *
   * @return the action.
   */
  Actions getAction();

  /**
   * A function to get the grantType of the view.
   *
   * @return the grant type.
   */
  GrantType getGrantType();
}