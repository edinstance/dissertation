package com.finalproject.backend.admin.queries;

import com.finalproject.backend.admin.dto.UserStats;
import com.finalproject.backend.admin.services.AdminService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

@DgsComponent
public class AdminQueries {

  private final AdminService adminService;

  public AdminQueries(AdminService adminService) {
    this.adminService = adminService;
  }

  @DgsQuery
  public UserStats getUserStats() {
    return adminService.getUserStats();
  }
}
