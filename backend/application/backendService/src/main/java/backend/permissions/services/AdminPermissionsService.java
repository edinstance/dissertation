package backend.permissions.services;

import backend.common.exceptions.UnauthorisedException;
import backend.common.helpers.AuthHelpers;
import backend.permissions.authorizers.AdminAuthorizer;
import backend.permissions.entities.PermissionView;
import backend.permissions.entities.PermissionsEntity;
import backend.permissions.repositories.PermissionViewRepository;
import backend.permissions.repositories.PermissionsRepository;
import backend.permissions.repositories.admin.AdminPermissionsRepository;
import backend.permissions.types.Actions;
import backend.permissions.types.AdminViewTypes;
import backend.permissions.types.Resources;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service manages the permissions.
 */
@Service
public class AdminPermissionsService {


  /**
   * Repository for accessing views of the permissions.
   */
  private final PermissionViewRepository permissionsViewRepository;

  /**
   * Repository for accessing admin permissions entities.
   */
  private final AdminPermissionsRepository adminPermissionsRepository;

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
   * Constructs a PermissionsService with the specified PermissionViewRepository.
   *
   * @param permissionsViewRepository  The repository for accessing views of the permissions.
   * @param adminPermissionsRepository The repository for accessing admin permissions entities.
   * @param authHelpers                The helper for authentication.
   * @param adminAuthorizer            The authorizer for admin permissions.
   */
  @Autowired
  public AdminPermissionsService(PermissionViewRepository permissionsViewRepository,
                                 AdminPermissionsRepository adminPermissionsRepository,
                                 AuthHelpers authHelpers, AdminAuthorizer adminAuthorizer,
                                 PermissionsRepository permissionsRepository) {
    this.permissionsViewRepository = permissionsViewRepository;
    this.adminPermissionsRepository = adminPermissionsRepository;
    this.authHelpers = authHelpers;
    this.adminAuthorizer = adminAuthorizer;
    this.permissionsRepository = permissionsRepository;
  }

  /**
   * This method gets all the admin permissions.
   *
   * @return all the admin permissions.
   */
  public List<PermissionView> getAllAdminPermissions() {

    boolean authorized = adminAuthorizer.authorize(
            authHelpers.getCurrentUserId(),
            Resources.ADMIN_PERMISSIONS,
            Actions.READ,
            AdminViewTypes.ALL

    );

    if (!authorized) {
      throw new UnauthorisedException("Admin is not authorized to view all admin permissions");
    }

    return permissionsViewRepository.getAllAdminPermissions(AdminViewTypes.ALL.getViewTypeName());
  }

  /**
   * This method gets the admin permissions for the current admin.
   *
   * @return the admins permissions.
   */
  public List<PermissionView> getCurrentAdminPermissions() {
    return permissionsViewRepository.getAdminPermissionsById(authHelpers.getCurrentUserId(),
            AdminViewTypes.ALL.getViewTypeName());
  }

  /**
   * This method gets the admin permissions for a specific admin.
   *
   * @return the admins permissions.
   */
  public List<PermissionsEntity> getAdminPermissionsById(final UUID userId) {
    boolean authorized = adminAuthorizer.authorize(
            authHelpers.getCurrentUserId(),
            Resources.ADMIN_PERMISSIONS,
            Actions.READ,
            AdminViewTypes.ALL
    );

    if (!authorized) {
      throw new UnauthorisedException(
              "Admin is not authorized to view different admin permissions"
      );
    }

    List<PermissionView> permissionViews = permissionsViewRepository.getAdminPermissionsById(
            userId,
            AdminViewTypes.ALL.getViewTypeName()
    );

    if (permissionViews == null || permissionViews.isEmpty()) {
      return List.of();
    }


    Set<UUID> permissionIds = permissionViews.stream()
            .map(view -> view.getId().getPermissionId())
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

    if (permissionIds.isEmpty()) {
      return List.of();
    }

    return permissionsRepository.findAllById(permissionIds);
  }

  /**
   * This method revokes a permission to an admin.
   *
   * @param adminId      the id of the admin.
   * @param permissionId the id of the permission.
   *
   * @return true if the permission was revoked, false otherwise.
   */
  public boolean revokeAdminPermission(final UUID adminId,
                                       final UUID permissionId) {
    boolean authorized = adminAuthorizer.authorize(
            authHelpers.getCurrentUserId(),
            Resources.ADMIN_PERMISSIONS,
            Actions.DELETE,
            AdminViewTypes.ALL
    );

    if (!authorized) {
      throw new UnauthorisedException(
              "Admin is not authorized to revoke admin permissions"
      );
    }

    adminPermissionsRepository.revokePermissionFromAdmin(
            adminId,
            authHelpers.getCurrentUserId(),
            permissionId
    );

    return true;
  }

  /**
   * This method grants a permission to an admin.
   *
   * @param adminId      the id of the admin.
   * @param permissionId the id of the permission.
   *
   * @return true if the permission was granted, false otherwise.
   */
  public boolean grantAdminPermissions(final UUID adminId,
                                       final UUID permissionId) {
    boolean authorized = adminAuthorizer.authorize(
            authHelpers.getCurrentUserId(),
            Resources.ADMIN_PERMISSIONS,
            Actions.CREATE,
            AdminViewTypes.ALL
    );

    if (!authorized) {
      throw new UnauthorisedException(
              "Admin is not authorized to grant admin permissions"
      );
    }

    adminPermissionsRepository.grantAdminPermission(
            adminId,
            authHelpers.getCurrentUserId(),
            permissionId
    );

    return true;
  }
}
