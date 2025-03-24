package com.finalproject.backend.admin.mutations;

import com.finalproject.backend.admin.entities.AdminEntity;
import com.finalproject.backend.admin.repositories.AdminRepository;
import com.finalproject.backend.admin.services.AdminService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

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


  @DgsMutation
  public AdminEntity createAdmin(@InputArgument final String userId) {
   return adminService.createAdmin(UUID.fromString(userId));
  }
}
