package com.finalproject.backend.admin.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;



/**
 * Represents an admin entity with basic information.
 */
@Entity
@Getter
@Setter
@Table(name = "admins")
public class AdminEntity {

  /**
   * The id of the admin.
   */
  @Id
  @Column(name = "user_id")
  private UUID userId;

  /**
   * If this admin is a super admin.
   */
  @Column(name = "is_super_admin")
  private boolean isSuperAdmin = false;

  /**
   * The status of this admin.
   */
  @Column(name = "status")
  private String status;

  /**
   * The id of the admin who created this admin.
   */
  @Column(name = "created_by")
  private UUID createdBy;

  /**
   * The id of who last updated this admin.
   */
  @Column(name = "last_updated_by")
  private UUID lastUpdatedBy;

  /**
   * If the admin is deleted or not.
   */
  @Column(name = "is_deleted")
  private Boolean isDeleted = false;

  /**
   * Default constructor.
   */
  public AdminEntity() {

  }


  /**
   * Constructor with admin information.
   *
   * @param userId the id of the admin.
   * @param isSuperAdmin if the admin is a super admin.
   * @param status the status of the admin.
   * @param createdBy who created the admin.
   * @param lastUpdatedBy who last updated the admin
   */
  public AdminEntity(UUID userId,
                     boolean isSuperAdmin, String status, UUID createdBy,
                     UUID lastUpdatedBy) {
    this.userId = userId;
    this.isSuperAdmin = isSuperAdmin;
    this.status = status;
    this.createdBy = createdBy;
    this.lastUpdatedBy = lastUpdatedBy;
  }
}
