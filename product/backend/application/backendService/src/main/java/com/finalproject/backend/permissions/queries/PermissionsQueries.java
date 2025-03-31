package com.finalproject.backend.permissions.queries;

import com.finalproject.backend.permissions.entities.PermissionsEntity;
import com.finalproject.backend.permissions.services.PermissionsService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class contains the queries for permissions.
 */
@DgsComponent
public class PermissionsQueries {

  /**
   * The service for managing permissions.
   */
  private final PermissionsService permissionsService;

  /**
   * Constructs an PermissionQueries with the specified PermissionsService.
   *
   * @param permissionsService The service for managing permissions.
   */
  @Autowired
  public PermissionsQueries(PermissionsService permissionsService) {
    this.permissionsService = permissionsService;
  }

  /**
   * This query gets all the permissions.
   *
   * @return all the permissions.
   */
  @DgsQuery
  public List<PermissionsEntity> getAllPermissions() {
    return permissionsService.getAllPermissions();
  }
}
