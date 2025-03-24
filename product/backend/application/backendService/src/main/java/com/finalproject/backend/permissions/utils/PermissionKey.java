package com.finalproject.backend.permissions.utils;

import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.Resources;

import java.util.Objects;
import java.util.UUID;

/**
 * An inner helper class for aggregating permission key fields.
 */
public class PermissionKey {
  private final UUID associatedUserId;
  private final Resources resource;
  private final Actions action;

  public PermissionKey(UUID associatedUserId, Resources resource, Actions action) {
    this.associatedUserId = associatedUserId;
    this.resource = resource;
    this.action = action;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PermissionKey that)) {
      return false;
    }
    return associatedUserId.equals(that.associatedUserId) &&
            resource == that.resource &&
            action == that.action;
  }

  @Override
  public int hashCode() {
    return Objects.hash(associatedUserId, resource, action);
  }
}