package com.finalproject.backend.permissions.repositories;

import com.finalproject.backend.permissions.entities.PermissionView;
import com.finalproject.backend.permissions.entities.ids.PermissionViewId;
import com.finalproject.backend.permissions.types.AdminViewTypes;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * This repository is responsible for managing the admin permissions.
 */
public interface PermissionViewRepository extends JpaRepository
        <PermissionView, PermissionViewId> {

  /**
   * This method gets all the admin permissions.
   *
   * @param viewType the view type.
   * @return all the admin permissions.
   */
  @Query(
          value = "SELECT * FROM get_all_admin_permissions("
                  + "CAST(:viewType AS admin_permission_view_type))",
          nativeQuery = true
  )
  List<PermissionView> getAllAdminPermissions(@Param("viewType")
                                                   AdminViewTypes viewType);

  /**
   * This method gets the admin permissions for a specific admin.
   *
   * @param viewType the view type.
   * @return all the admin permissions.
   */
  @Query(
          value = "SELECT * FROM get_admin_permissions_by_id(:userId,"
                  + "CAST(:viewType AS admin_permission_view_type))",
          nativeQuery = true
  )
  List<PermissionView> getAdminPermissionsById(@Param("userId") UUID adminId,
                                              @Param("viewType") AdminViewTypes viewType);
}
