package backend.permissions.entities;

import backend.permissions.entities.ids.RolePermissionId;
import backend.permissions.types.GrantType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a role permission entity.
 */
@Entity
@Getter
@Setter
@Table(name = "role_permissions")
public class RolePermissionEntity {

  /**
   * The id of the role permission.
   */
  @EmbeddedId
  private RolePermissionId id;

  /**
   * The role.
   */
  @MapsId
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id")
  private RoleEntity role;

  /**
   * The permission.
   */
  @MapsId
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "permission_id")
  private PermissionsEntity permission;

  /**
   * The grant type of the permission.
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "grant_type")
  private GrantType grantType;

  /**
   * Default constructor.
   */
  public RolePermissionEntity() {
  }

  /**
   * Role permissions constructor with options.
   *
   * @param role       the role.
   * @param permission the permission.
   */
  public RolePermissionEntity(RoleEntity role, PermissionsEntity permission,
                              GrantType grantType) {
    this.role = role;
    this.permission = permission;
    this.grantType = grantType;
  }
}