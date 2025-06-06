package backend.permissions.mutations;

import backend.common.config.logging.AppLogger;
import backend.common.dto.MutationResponse;
import backend.permissions.services.AdminPermissionsService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class contains the mutations for the admin permissions.
 */
@DgsComponent
public class AdminPermissionsMutations {

  /**
   * The service for managing permissions.
   */
  private final AdminPermissionsService adminPermissionsService;

  /**
   * Constructs an AdminPermissionMutations with the specified PermissionsService.
   *
   * @param adminPermissionsService The service for managing permissions.
   */
  @Autowired
  public AdminPermissionsMutations(AdminPermissionsService adminPermissionsService) {
    this.adminPermissionsService = adminPermissionsService;
  }

  /**
   * This query revokes the admin permissions for a specified admin.
   *
   * @return a response about the success of the operation.
   */
  @DgsMutation
  public MutationResponse revokeAdminPermission(@InputArgument final String adminId,
                                                @InputArgument final String permissionId) {

    boolean result = adminPermissionsService.revokeAdminPermission(
            UUID.fromString(adminId),
            UUID.fromString(permissionId));

    if (result) {
      return new MutationResponse(true, "Admin permission revoked successfully");
    }
    AppLogger.error("Admin permission revoking failed for admin with id: " + adminId);
    return new MutationResponse(false, "Error revoking admin permission");
  }

  /**
   * This query grants the admin permissions for a specified admin.
   *
   * @return a response about the success of the operation.
   */
  @DgsMutation
  public MutationResponse grantAdminPermission(@InputArgument final String adminId,
                                               @InputArgument final String permissionId) {

    boolean result = adminPermissionsService.grantAdminPermissions(
            UUID.fromString(adminId),
            UUID.fromString(permissionId));

    if (result) {
      return new MutationResponse(true, "Admin permission granted successfully");
    }

    AppLogger.error("Admin permission granting failed for admin with id: " + adminId
            + " for permission with id: " + permissionId);

    return new MutationResponse(false, "Error granting admin permission");
  }
}
