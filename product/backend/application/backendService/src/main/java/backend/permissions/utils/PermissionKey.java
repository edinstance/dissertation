package backend.permissions.utils;

import backend.permissions.types.Actions;
import backend.permissions.types.Resources;
import java.util.Objects;
import java.util.UUID;

/**
 * A helper class for aggregating permission key fields.
 */
public class PermissionKey {
  private final UUID associatedUserId;
  private final Resources resource;
  private final Actions action;

  /**
   * A constructor for the permission key.
   *
   * @param associatedUserId the user id.
   * @param resource the resource.
   * @param action the action.
   */
  public PermissionKey(UUID associatedUserId, Resources resource, Actions action) {
    this.associatedUserId = associatedUserId;
    this.resource = resource;
    this.action = action;
  }

  /**
   * An override of the equals' method.
   *
   * @param o the object to compare.
   * @return if the objects are equal.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PermissionKey that)) {
      return false;
    }
    return associatedUserId.equals(that.associatedUserId)
            && resource == that.resource
            && action == that.action;
  }

  /**
   * An override of the hash code method.
   *
   * @return the hash code.
   */
  @Override
  public int hashCode() {
    return Objects.hash(associatedUserId, resource, action);
  }
}