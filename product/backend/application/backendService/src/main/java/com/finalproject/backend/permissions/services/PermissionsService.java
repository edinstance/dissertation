package com.finalproject.backend.permissions.services;

import com.finalproject.backend.common.exceptions.UnauthorisedException;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.finalproject.backend.permissions.authorizers.AdminAuthorizer;
import com.finalproject.backend.permissions.entities.PermissionView;
import com.finalproject.backend.permissions.repositories.PermissionViewRepository;
import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.AdminViewTypes;
import com.finalproject.backend.permissions.types.Resources;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service manages the permissions.
 */
@Service
public class PermissionsService {


  /**
   * Repository for accessing views of the permissions.
   */
  private final PermissionViewRepository permissionsViewRepository;

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
   * @param permissionsViewRepository The repository for accessing views of the permissions.
   * @param authHelpers               The helper for authentication.
   * @param adminAuthorizer           The authorizer for admin permissions.
   */
  @Autowired
  public PermissionsService(PermissionViewRepository permissionsViewRepository,
                            AuthHelpers authHelpers, AdminAuthorizer adminAuthorizer) {
    this.permissionsViewRepository = permissionsViewRepository;
    this.authHelpers = authHelpers;
    this.adminAuthorizer = adminAuthorizer;
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
   * This method gets the admin permissions for a specific admin.
   *
   * @return the admins permissions.
   */
  public List<PermissionView> getCurrentAdminPermissions() {
    return permissionsViewRepository.getAdminPermissionsById(authHelpers.getCurrentUserId(),
            AdminViewTypes.ALL.getViewTypeName());
  }
}
