package com.finalproject.backend.permissions.repositories.admin;

import com.finalproject.backend.permissions.entities.admin.AdminPermissionsEntity;
import com.finalproject.backend.permissions.entities.admin.ids.AdminPermissionsEntityId;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * This repository is responsible for managing the admin permissions.
 */
public interface AdminPermissionsRepository extends JpaRepository
        <AdminPermissionsEntity, AdminPermissionsEntityId> {

  /**
   * Function for revoking permissions from an admin.
   *
   * @param userId       the id of the admin that the permission will be revoked from.
   * @param adminId      the id of the admin performing this operation.
   * @param permissionId the id of the permission to revoke.
   */
  @Modifying
  @Query(value = "CALL revoke_permission_from_admin(:userId, :adminId, :permissionId)",
          nativeQuery = true)
  @Transactional
  void revokePermissionFromAdmin(
          @Param("userId") UUID userId,
          @Param("adminId") UUID adminId,
          @Param("permissionId") UUID permissionId
  );

  /**
   * Function for granting permissions to an admin.
   *
   * @param userId   the id of the admin that the permission will be granted to.
   * @param adminId  the id of the admin performing this operation.
   * @param permissionId the id of the permission to grant.
   */
  @Modifying
  @Query(value = "CALL grant_admin_permission(:adminId, :performingAdminId, :permissionId)",
          nativeQuery = true)
  @Transactional
  void grantAdminPermission(
          @Param("adminId") UUID userId,
          @Param("performingAdminId") UUID adminId,
          @Param("permissionId") UUID permissionId
  );
}
