package com.finalproject.backend.permissions.queries;

import com.finalproject.backend.permissions.entities.PermissionView;
import com.finalproject.backend.permissions.services.PermissionsService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class contains the queries for the admin permissions.
 */
@DgsComponent
public class AdminPermissionQueries {

  /**
   * The service for managing permissions.
   */
  private final PermissionsService permissionsService;

  /**
   * Constructs an AdminPermissionQueries with the specified PermissionsService.
   *
   * @param permissionsService The service for managing permissions.
   */
  @Autowired
  public AdminPermissionQueries(PermissionsService permissionsService) {
    this.permissionsService = permissionsService;
  }

  /**
   * This query gets all the admin permissions.
   *
   * @return all the admin permissions.
   */
  @DgsQuery
  public List<PermissionView> getAllAdminPermissions() {
    return permissionsService.getAllAdminPermissions();
  }

  /**
   * This query gets the admin permissions for the current admin.
   *
   * @return the admins permissions.
   */
  @DgsQuery
  public List<PermissionView> getCurrentAdminPermissions() {
    return permissionsService.getCurrentAdminPermissions();
  }
}
