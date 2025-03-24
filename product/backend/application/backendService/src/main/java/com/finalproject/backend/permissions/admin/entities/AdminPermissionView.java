package com.finalproject.backend.permissions.admin.entities;

import com.finalproject.backend.permissions.admin.entities.ids.AdminPermissionId;
import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.GrantType;
import com.finalproject.backend.permissions.types.PermissionView;
import com.finalproject.backend.permissions.types.Resources;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

/**
 * A read-only mapping of the admin_permissions_view.
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "admin_permissions_view")
public class AdminPermissionView implements PermissionView {

  /**
   * The composite primary key containing adminId, permissionId, resourceId, and actionId.
   */
  @EmbeddedId
  private AdminPermissionId id;

  /**
   * The grant type of the permission.
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "grant_type")
  private GrantType grantType;

  /**
   * The resource associated with the permission.
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "resource")
  private Resources resource;

  /**
   * The action granted by the permission.
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "action")
  private Actions action;

  /**
   * This method gets the id of the view.
   *
   * @return the admin id.
   */
  @Override
  public UUID getAssociatedUserId() {
    return id.getAdminId();
  }

  /**
   * Default constructor.
   */
  public AdminPermissionView() {
  }

  /**
   * Constructor with options.
   *
   * @param id        the composite primary key.
   * @param grantType the grant type.
   * @param resource  the resource.
   * @param action    the action.
   */
  public AdminPermissionView(AdminPermissionId id,
                             GrantType grantType, Resources resource, Actions action) {
    this.id = id;
    this.grantType = grantType;
    this.resource = resource;
    this.action = action;
  }
}
