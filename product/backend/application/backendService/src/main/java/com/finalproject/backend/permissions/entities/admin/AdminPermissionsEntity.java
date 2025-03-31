package com.finalproject.backend.permissions.entities.admin;

import com.finalproject.backend.admin.entities.AdminEntity;
import com.finalproject.backend.permissions.entities.PermissionsEntity;
import com.finalproject.backend.permissions.entities.admin.ids.AdminPermissionsEntityId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an admin permission entity.
 */
@Entity
@Getter
@Setter
@Table(name = "admin_permissions")
public class AdminPermissionsEntity {

  /**
   * The id of the admin permissions entity.
   */
  @EmbeddedId
  private AdminPermissionsEntityId id;

  /**
   * The admin related to the permissions.
   */
  @MapsId
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "admin_id")
  private AdminEntity admin;

  /**
   * The permission related to this.
   */
  @MapsId
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "permission_id")
  private PermissionsEntity permission;

  /**
   * The grant type of the permission.
   */
  @Column(name = "grant_type")
  private String grantType;


  /**
   * Default constructor.
   */
  public AdminPermissionsEntity() {
  }

  /**
   * The constructor with the information.
   *
   * @param admin      the admin for the entity.
   * @param permission the permission for the entity.
   * @param grantType  the grant type of the permission.
   */
  public AdminPermissionsEntity(AdminEntity admin, PermissionsEntity permission, String grantType) {
    this.admin = admin;
    this.permission = permission;
    this.grantType = grantType;
  }
}