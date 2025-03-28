package com.finalproject.backend.permissions.services;

import com.finalproject.backend.common.exceptions.UnauthorisedException;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.finalproject.backend.permissions.authorizers.AdminAuthorizer;
import com.finalproject.backend.permissions.entities.PermissionView;
import com.finalproject.backend.permissions.entities.PermissionsEntity;
import com.finalproject.backend.permissions.repositories.PermissionViewRepository;
import com.finalproject.backend.permissions.repositories.PermissionsRepository;
import com.finalproject.backend.permissions.repositories.admin.AdminPermissionsRepository;
import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.AdminViewTypes;
import com.finalproject.backend.permissions.types.Resources;
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
}
