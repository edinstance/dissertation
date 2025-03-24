package com.finalproject.backend.permissions.entities;

import com.finalproject.backend.permissions.entities.ids.PermissionViewId;
import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.GrantType;
import com.finalproject.backend.permissions.types.Resources;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

/**
 * A read-only mapping of the admin_permissions_view.
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "permissions_view")
public class PermissionView {

  /**
   * The composite primary key containing userId, permissionId, resourceId, and actionId.
   */
  @EmbeddedId
  private PermissionViewId id;

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
   * This method gets the userId from the composite key.
   *
   * @return the admin id.
   */
  public UUID getAssociatedUserId() {
    return id.getUserId();
  }

  /**
   * Default constructor.
   */
  public PermissionView() {
  }

  /**
   * Constructor with options.
   *
   * @param id        the composite primary key.
   * @param grantType the grant type.
   * @param resource  the resource.
   * @param action    the action.
   */
  public PermissionView(PermissionViewId id,
                        GrantType grantType, Resources resource, Actions action) {
    this.id = id;
    this.grantType = grantType;
    this.resource = resource;
    this.action = action;
  }
}
