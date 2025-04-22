package backend.admin.dto;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * This is a data transfer object for the Admin entity.
 */
@Getter
@Setter
public class Admin {

  /**
   * This is the id of the admin.
   */
  private UUID userId;

  /**
   * This is if the admin is a super admin.
   */
  private boolean isSuperAdmin;

  /**
   * This is the status of the admin.
   */
  private String status;

  /**
   * This is whether the admin is deleted or not.
   */
  private Boolean isDeleted;

  /**
   * This is the email of the admin.
   */
  private String email;

  /**
   * Default constructor.
   */
  public Admin() {
  }

  /**
   * Constructor with parameters.
   *
   * @param userId       The id of the admin.
   * @param isSuperAdmin If the admin is a super admin.
   * @param status       The status of the admin.
   * @param isDeleted    If the admin is deleted.
   * @param email        The email of the admin.
   */
  public Admin(UUID userId, boolean isSuperAdmin,
               String status, Boolean isDeleted,
               String email) {
    this.userId = userId;
    this.isSuperAdmin = isSuperAdmin;
    this.status = status;
    this.isDeleted = isDeleted;
    this.email = email;
  }

}
