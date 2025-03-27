package com.finalproject.backend.admin.mappers;

import com.finalproject.backend.admin.dto.Admin;
import com.finalproject.backend.admin.entities.AdminEntity;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for AdminEntity and Admin.
 */
public class AdminMapper {

  /**
   * Map an AdminEntity to an Admin.
   *
   * @param adminEntity the AdminEntity to map.
   * @return the mapped Admin.
   */
  public static Admin mapAdminEntityToAdmin(AdminEntity adminEntity) {
    if (adminEntity == null) {
      return null;
    }

    return new Admin(
            adminEntity.getUserId(),
            adminEntity.isSuperAdmin(),
            adminEntity.getStatus(),
            adminEntity.getIsDeleted(),
            adminEntity.getUser() != null ? adminEntity.getUser().getEmail() : null
    );
  }

  /**
   * Map a list of AdminEntity objects to a list of Admins.
   *
   * @param adminEntities the list of AdminEntity objects to map.
   * @return the list of mapped Admins.
   */
  public static List<Admin> mapAdminEntityListToAdmins(List<AdminEntity> adminEntities) {
    if (adminEntities == null || adminEntities.isEmpty()) {
      return List.of();
    }

    return adminEntities.stream()
            .map(AdminMapper::mapAdminEntityToAdmin)
            .collect(Collectors.toList());
  }
}