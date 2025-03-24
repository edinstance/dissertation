package com.finalproject.backend.permissions.authorizers;

import com.finalproject.backend.admin.entities.AdminEntity;
import com.finalproject.backend.admin.repositories.AdminRepository;
import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.permissions.entities.PermissionView;
import com.finalproject.backend.permissions.repositories.PermissionViewRepository;
import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.AdminViewTypes;
import com.finalproject.backend.permissions.types.GrantType;
import com.finalproject.backend.permissions.types.Resources;
import com.finalproject.backend.permissions.types.ViewTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * This class is responsible for checking if an admin has permissions.
 */
@Component
public class AdminAuthorizer implements PermissionAuthorizer {

  /**
   * Repository for accessing admin permissions.
   */
  private final PermissionViewRepository permissionViewRepository;

  /**
   * Repository for accessing admin data.
   */
  private final AdminRepository adminRepository;

  /**
   * Constructor for the admin authorizer.
   *
   * @param adminRepository          the repository for accessing admin data.
   * @param permissionViewRepository the repository for accessing permissions.
   */
  @Autowired
  public AdminAuthorizer(AdminRepository adminRepository,
                         PermissionViewRepository permissionViewRepository) {
    this.adminRepository = adminRepository;
    this.permissionViewRepository = permissionViewRepository;
  }

  /**
   * This method checks if an admin has permission to access a resource.
   *
   * @param adminId   the user id.
   * @param resource  the resource.
   * @param action    the action.
   * @param viewType  the view type.
   * @return if the user is authorized.
   */
  @Override
  public boolean authorize(UUID adminId, Resources resource,
                           Actions action,
                           ViewTypes viewType) {

    AdminEntity admin = adminRepository.findById(adminId).orElse(null);

    if (admin == null) {
      return false;
    }

    if (admin.isSuperAdmin()) {
      return true;
    }

    List<PermissionView> permissions =
            permissionViewRepository.getAdminPermissionsById(adminId,(AdminViewTypes) viewType);

    List<PermissionView> effectivePermissions = getEffectivePermissions(permissions);

    for (PermissionView permission : effectivePermissions) {
      if (permission.getId().getUserId().equals(adminId)
              && permission.getResource().equals(resource)
              && permission.getAction().equals(action)) {
        return true;
      }
    }

    return false;
  }
}
