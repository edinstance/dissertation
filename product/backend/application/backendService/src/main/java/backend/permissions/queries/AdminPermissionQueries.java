package backend.permissions.queries;

import backend.permissions.entities.PermissionView;
import backend.permissions.entities.PermissionsEntity;
import backend.permissions.services.AdminPermissionsService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class contains the queries for the admin permissions.
 */
@DgsComponent
public class AdminPermissionQueries {

  /**
   * The service for managing admin permissions.
   */
  private final AdminPermissionsService adminPermissionsService;

  /**
   * Constructs an AdminPermissionQueries with the specified PermissionsService.
   *
   * @param adminPermissionsService The service for managing permissions.
   */
  @Autowired
  public AdminPermissionQueries(AdminPermissionsService adminPermissionsService) {
    this.adminPermissionsService = adminPermissionsService;
  }

  /**
   * This query gets all the admin permissions.
   *
   * @return all the admin permissions.
   */
  @DgsQuery
  public List<PermissionView> getAllAdminPermissions() {
    return adminPermissionsService.getAllAdminPermissions();
  }

  /**
   * This query gets the admin permissions for the current admin.
   *
   * @return the admins permissions.
   */
  @DgsQuery
  public List<PermissionView> getCurrentAdminPermissions() {
    return adminPermissionsService.getCurrentAdminPermissions();
  }

  /**
   * This query gets the admin permissions for a specified admin.
   *
   * @param adminId The id of the admin.
   */
  @DgsQuery
  public List<PermissionsEntity> getAdminPermissionsByAdminId(@InputArgument String adminId) {
    return adminPermissionsService.getAdminPermissionsById(UUID.fromString(adminId));
  }
}
