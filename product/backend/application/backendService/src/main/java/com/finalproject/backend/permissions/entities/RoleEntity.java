package com.finalproject.backend.permissions.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a role entity.
 */
@Entity
@Getter
@Setter
@Table(name = "roles")
public class RoleEntity {

  /**
   * The id of the role entity.
   */
  @Id
  @Column(name = "role_id", nullable = false)
  private UUID id;

  /**
   * The name of the role entity.
   */
  @Column(name = "role_name", nullable = false, length = 100)
  private String roleName;

  /**
   * The description of the role entity.
   */
  @Column(name = "description", length = Integer.MAX_VALUE)
  private String description;

  /**
   * The permissions of the role entity.
   */
  @OneToMany(mappedBy = "role")
  private Set<RolePermissionEntity> rolePermissions = new LinkedHashSet<>();

  /**
   * Default constructor.
   */
  public RoleEntity() {}

  /**
   * Constructor with options.
   *
   * @param id the id of the role.
   * @param roleName the name.
   * @param description the description.
   */
  public RoleEntity(UUID id, String roleName, String description) {
    this.id = id;
    this.roleName = roleName;
    this.description = description;
  }
}
