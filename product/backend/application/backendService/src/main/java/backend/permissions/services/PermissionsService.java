package backend.permissions.services;

import backend.common.exceptions.UnauthorisedException;
import backend.common.helpers.AuthHelpers;
import backend.permissions.authorizers.AdminAuthorizer;
import backend.permissions.entities.PermissionsEntity;
import backend.permissions.repositories.PermissionsRepository;
import backend.permissions.types.Actions;
import backend.permissions.types.AdminViewTypes;
import backend.permissions.types.Resources;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service manages the permissions.
 */
@Service
public class PermissionsService {

  /**
   * Repository for managing permissions entities.
   */
  private final PermissionsRepository permissionsRepository;

  /**
   * The helper for authentication.
   */
  private final AuthHelpers authHelpers;

  /**
   * The authorizer for admin permissions.
   */
  private final AdminAuthorizer adminAuthorizer;

  /**
   * Constructs a PermissionsService.
   *
   * @param permissionsRepository The repository for managing permissions entities.
   * @param authHelpers           The helper for authentication.
   * @param adminAuthorizer       The authorizer for admin permissions.
   */
  @Autowired
  public PermissionsService(
          final PermissionsRepository permissionsRepository,
          final AuthHelpers authHelpers,
          final AdminAuthorizer adminAuthorizer) {
    this.permissionsRepository = permissionsRepository;
    this.authHelpers = authHelpers;
    this.adminAuthorizer = adminAuthorizer;
  }


  /**
   * This method retrieves all permissions.
   *
   * @return a list of all permissions.
   */
  public List<PermissionsEntity> getAllPermissions() {
    boolean authorized = adminAuthorizer.authorize(
            authHelpers.getCurrentUserId(),
            Resources.PERMISSIONS,
            Actions.READ,
            AdminViewTypes.ALL
    );

    if (!authorized) {
      throw new UnauthorisedException(
              "Admin is not authorized to read permissions"
      );
    }

    return permissionsRepository.findAll();
  }

  /**
   * This method creates a new permission and the resource and
   * action associated with it if they do not exist.
   *
   * @param permissionDescription the description of the permission.
   * @param action                the action associated with the permission.
   * @param actionDescription     the description of the action.
   * @param resource              the resource associated with the permission.
   * @param resourceDescription   the description of the resource.
   *
   * @return true if the permission was created, false otherwise.
   */
  public boolean createPermission(
          final String permissionDescription,
          final Actions action,
          final String actionDescription,
          final Resources resource,
          final String resourceDescription) {

    boolean authorized = adminAuthorizer.authorize(
            authHelpers.getCurrentUserId(),
            Resources.PERMISSIONS,
            Actions.CREATE,
            AdminViewTypes.ALL
    );

    if (!authorized) {
      throw new UnauthorisedException(
              "Admin is not authorized to create permissions"
      );
    }

    permissionsRepository.createPermission(
            permissionDescription,
            action.name(),
            actionDescription,
            resource.name(),
            resourceDescription
    );

    return true;
  }
}
