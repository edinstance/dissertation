package com.finalproject.backend.permissions.admin.entities;

import com.finalproject.backend.admin.entities.AdminEntity;
import com.finalproject.backend.permissions.admin.entities.ids.AdminRolesEntityId;
import com.finalproject.backend.permissions.entities.RoleEntity;
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
 * Represents an admin roles entity.
 */
@Entity
@Getter
@Setter
@Table(name = "admin_roles")
public class AdminRolesEntity {

  @EmbeddedId
  private AdminRolesEntityId id;

  /**
   * The admin.
   */
  @MapsId
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "admin_id", nullable = false)
  private AdminEntity admin;

  /**
   * The role.
   */
  @MapsId
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "role_id", nullable = false)
  private RoleEntity role;

  /**
   * Default constructor.
   */
  public AdminRolesEntity() {}

  /**
   * Constructor with an admin and a role.
   *
   * @param admin the admin.
   * @param role the role for the admin.
   */
  public AdminRolesEntity(AdminEntity admin, RoleEntity role) {
    this.admin = admin;
    this.role = role;
  }
}