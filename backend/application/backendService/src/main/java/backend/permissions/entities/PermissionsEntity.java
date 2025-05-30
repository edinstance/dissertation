package backend.permissions.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a permission entity.
 */
@Getter
@Setter
@Entity
@Table(name = "permissions")
public class PermissionsEntity {

  /**
   * The id of the permission.
   */
  @Id
  @Column(name = "permission_id", nullable = false)
  private UUID id;

  /**
   * The resource it is linked to.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "resource_id")
  private ResourcesEntity resource;

  /**
   * The actions it can perform.
   */
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "action_id", nullable = false)
  private ActionsEntity action;

  /**
   * The description of the permission.
   */
  @Column(name = "description", length = Integer.MAX_VALUE)
  private String description;

  /**
   * The role permissions.
   */
  @OneToMany(mappedBy = "permission")
  private Set<RolePermissionEntity> rolePermissions = new LinkedHashSet<>();

  /**
   * Default constructor.
   */
  public PermissionsEntity() {
  }

  /**
   * Permissions constructor with options.
   *
   * @param resource    the resource of the permissions.
   * @param action      the actions the permission grants.
   * @param description the description of the permissions.
   */
  public PermissionsEntity(ResourcesEntity resource, ActionsEntity action, String description) {
    this.resource = resource;
    this.action = action;
    this.description = description;
  }

}