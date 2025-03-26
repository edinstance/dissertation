package com.finalproject.backend.admin.mutations;

import com.finalproject.backend.admin.services.AdminService;
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
}
