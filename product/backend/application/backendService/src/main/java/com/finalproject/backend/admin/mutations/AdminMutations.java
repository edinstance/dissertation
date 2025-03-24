package com.finalproject.backend.admin.mutations;

import com.finalproject.backend.admin.entities.AdminEntity;
import com.finalproject.backend.admin.services.AdminService;
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
   * @return the new admin entity.
   */
  @DgsMutation
  public AdminEntity createAdmin(@InputArgument final String userId) {
    return adminService.createAdmin(UUID.fromString(userId));
  }
}
