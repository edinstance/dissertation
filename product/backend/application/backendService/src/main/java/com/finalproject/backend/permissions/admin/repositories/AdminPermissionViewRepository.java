package com.finalproject.backend.permissions.admin.repositories;

import com.finalproject.backend.permissions.admin.entities.AdminPermissionView;
import com.finalproject.backend.permissions.admin.entities.ids.AdminPermissionId;
import com.finalproject.backend.permissions.types.AdminViewTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * This repository is responsible for managing the admin permissions.
 */
public interface AdminPermissionViewRepository extends JpaRepository
        <AdminPermissionView, AdminPermissionId> {

  @Query(
          value = "SELECT * FROM get_admin_permissions(CAST(:viewType AS admin_permission_view_type))",
          nativeQuery = true
  )
  List<AdminPermissionView> getAllAdminPermissions(@Param("viewType") AdminViewTypes viewType);
}
