package backend.permissions.entities.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

/**
 * Composite id for the role permissions.
 */
@Getter
@Setter
@Embeddable
public class RolePermissionId implements java.io.Serializable {

  @Serial
  private static final long serialVersionUID = 3224098234491909308L;

  /**
   * The role id.
   */
  @Column(name = "role_id", nullable = false)
  private UUID roleId;

  /**
   * The permission id.
   */
  @Column(name = "permission_id", nullable = false)
  private UUID permissionId;

  /**
   * A function to check if objects are the same.
   *
   * @param o the object to compare with.
   *
   * @return the result of the comparison.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    RolePermissionId entity = (RolePermissionId) o;
    return Objects.equals(this.permissionId, entity.permissionId)
            && Objects.equals(this.roleId, entity.roleId);
  }

  /**
   * A function to create a hash code.
   *
   * @return the hash.
   */
  @Override
  public int hashCode() {
    return Objects.hash(permissionId, roleId);
  }

}