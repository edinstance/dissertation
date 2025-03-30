package com.finalproject.backend.permissions.repositories;

import com.finalproject.backend.permissions.entities.PermissionsEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for managing permissions entities.
 */
public interface PermissionsRepository extends JpaRepository<PermissionsEntity, UUID> {

  /**
   * Function for creating a new permission.
   *
   * @param permissionDescription the description of the permission.
   * @param action                the action associated with the permission.
   * @param actionDescription     the description of the action.
   * @param resource              the resource associated with the permission.
   * @param resourceDescription   the description of the resource.
   */
  @Modifying
  @Query(value = "CALL create_permission(:permissionDescription, :action, "
          + ":actionDescription, :resource, :resourceDescription)",
          nativeQuery = true)
  @Transactional
  void createPermission(
          @Param("permissionDescription") String permissionDescription,
          @Param("action") String action,
          @Param("actionDescription") String actionDescription,
          @Param("resource") String resource,
          @Param("resourceDescription") String resourceDescription

  );
}
