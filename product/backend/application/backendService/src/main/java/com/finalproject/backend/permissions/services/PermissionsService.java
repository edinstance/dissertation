package com.finalproject.backend.permissions.services;

import com.finalproject.backend.common.helpers.AuthHelpers;
import com.finalproject.backend.permissions.entities.PermissionView;
import com.finalproject.backend.permissions.repositories.PermissionViewRepository;
import com.finalproject.backend.permissions.types.AdminViewTypes;
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

  private final AuthHelpers authHelpers;

  /**
   * Constructs a PermissionsService with the specified PermissionViewRepository.
   *
   * @param permissionsViewRepository The repository for accessing views of the permissions.
   */
  @Autowired
  public PermissionsService(PermissionViewRepository permissionsViewRepository,
                            AuthHelpers authHelpers) {
    this.permissionsViewRepository = permissionsViewRepository;
    this.authHelpers = authHelpers;
  }

  /**
   * This method gets all the admin permissions.
   *
   * @return all the admin permissions.
   */
  public List<PermissionView> getAllAdminPermissions() {
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
