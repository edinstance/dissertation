package com.finalproject.backend.permissions.authorizers;

import com.finalproject.backend.admin.entities.AdminEntity;
import com.finalproject.backend.admin.repositories.AdminRepository;
import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.permissions.admin.entities.AdminPermissionView;
import com.finalproject.backend.permissions.admin.repositories.AdminPermissionViewRepository;
import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.AdminViewTypes;
import com.finalproject.backend.permissions.types.GrantType;
import com.finalproject.backend.permissions.types.Resources;
import com.finalproject.backend.permissions.types.ViewTypes;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminAuthorizer implements PermissionAuthorizer {

  private final AdminPermissionViewRepository adminPermissionViewRepository;
  private final AdminRepository adminRepository;

  @Autowired
  public AdminAuthorizer(AdminRepository adminRepository, AdminPermissionViewRepository adminPermissionViewRepository) {
    this.adminRepository = adminRepository;
    this.adminPermissionViewRepository = adminPermissionViewRepository;
  }

  @Override
  public boolean authorize(UUID adminId, Resources resource,
                               Actions action, GrantType grantType,
                               ViewTypes viewType) {

    if (!(viewType instanceof AdminViewTypes)) {
      AppLogger.error("AdminPermissionChecker.hasPermission: Expected AdminPermissionViewTypes, but received: " + viewType);
      throw new IllegalArgumentException("Expected an AdminPermissionViewTypes, got: " + viewType);
    }

    AdminEntity admin = adminRepository.findById(adminId).orElse(null);

    if (admin == null) {
      return false;
    }

    if (admin.isSuperAdmin()) {
      return true;
    }

    List<AdminPermissionView> permissions = adminPermissionViewRepository.getAllAdminPermissions((AdminViewTypes) viewType);

    for (AdminPermissionView permission : permissions) {
      if (permission.getId().getAdminId().equals(adminId) &&
              permission.getResource().equals(resource) &&
              permission.getAction().equals(action) &&
              permission.getGrantType().equals(grantType)) {
        return true;
      }
    }

    return false;
  }
}
