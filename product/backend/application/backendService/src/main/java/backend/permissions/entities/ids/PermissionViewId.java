package backend.permissions.entities.ids;

import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

/**
 * A composite id for the permissions' entity.
 */
@Getter
@Setter
@Embeddable
public class PermissionViewId implements Serializable {

  @Serial
  private static final long serialVersionUID = -4085129020358584838L;

  /**
   * The id of the user.
   */
  private UUID userId;

  /**
   * The id of the permission.
   */
  private UUID permissionId;

  /**
   * The id of the resource.
   */
  private UUID resourceId;

  /**
   * The id of the action.
   */
  private UUID actionId;

  /**
   * Default constructor.
   */
  public PermissionViewId() {
  }

  /**
   * Constructs a PermissionViewId with the specified userId,
   * permissionId, resourceId, and actionId.
   *
   * @param userId       The id of the user.
   * @param permissionId The id of the permission.
   * @param resourceId   The id of the resource.
   * @param actionId     The id of the action.
   */
  public PermissionViewId(UUID userId, UUID permissionId, UUID resourceId, UUID actionId) {
    this.userId = userId;
    this.permissionId = permissionId;
    this.resourceId = resourceId;
    this.actionId = actionId;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param o the object to compare with.
   *
   * @return true if this object is the same as the o argument; false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    PermissionViewId that = (PermissionViewId) o;
    return Objects.equals(userId, that.userId)
            && Objects.equals(permissionId, that.permissionId)
            && Objects.equals(resourceId, that.resourceId)
            && Objects.equals(actionId, that.actionId);
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return the hash code.
   */
  @Override
  public int hashCode() {
    return Objects.hash(userId, permissionId, resourceId, actionId);
  }
}
