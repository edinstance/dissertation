package backend.permissions.entities.admin.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

/**
 * A composite id for the admin permissions entity.
 */
@Getter
@Setter
@Embeddable
public class AdminPermissionsEntityId implements java.io.Serializable {

  @Serial
  private static final long serialVersionUID = -7478675555774598369L;

  /**
   * The id of the admin.
   */
  @Column(name = "admin_id", nullable = false)
  private UUID adminId;

  /**
   * The id of the permission.
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
    AdminPermissionsEntityId entity = (AdminPermissionsEntityId) o;
    return Objects.equals(this.permissionId, entity.permissionId)
            && Objects.equals(this.adminId, entity.adminId);
  }

  /**
   * A function to create a hash code.
   *
   * @return the hash.
   */
  @Override
  public int hashCode() {
    return Objects.hash(permissionId, adminId);
  }

}