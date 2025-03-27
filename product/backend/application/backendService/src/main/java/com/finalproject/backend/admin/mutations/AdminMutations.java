package com.finalproject.backend.admin.mutations;

import com.finalproject.backend.admin.services.AdminService;
import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.common.dto.MutationResponse;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * This class contains the admin mutations.
 */
@DgsComponent
public class AdminMutations {

  /**
   * The admin service to use.
   */
  private final AdminService adminService;

  /**
   * Constructor for the AdminMutations class.
   *
   * @param adminService the admin service to use.
   */
  @Autowired
  public AdminMutations(AdminService adminService) {
    this.adminService = adminService;
  }


  /**
   * A mutation to create an admin.
   *
   * @param userId the user to turn into an admin.
   * @return a mutation response based on the outcome.
   */
  @DgsMutation
  public MutationResponse createAdmin(@InputArgument final String userId) {
    adminService.createAdmin(UUID.fromString(userId));

    return new MutationResponse(true, "Admin created successfully");
  }

  /**
   * A mutation to promote an admin to a super admin.
   *
   * @param userId the admin id to turn into a super admin .
   * @return a mutation response based on the outcome.
   */
  @DgsMutation
  public MutationResponse promoteAdminToSuperAdmin(@InputArgument final String userId) {
    adminService.promoteAdminToSuperUser(UUID.fromString(userId));

    return new MutationResponse(true, "Admin promoted successfully");
  }

  /**
   * A mutation to deactivate an admin.
   *
   * @param userId the admin id of the admin to deactivate.
   * @return a mutation response based on the outcome.
   */
  @DgsMutation
  public MutationResponse deactivateAdmin(@InputArgument final String userId) {
    Boolean result = adminService.deactivateAdmin(UUID.fromString(userId));

    if (result) {
      return new MutationResponse(true, "Admin deactivated successfully");
    }
    AppLogger.error("Admin deactivation failed for admin with id: " + userId);
    return new MutationResponse(false, "Error deactivating admin");
  }
}
