package backend.admin.queries;

import backend.admin.dto.Admin;
import backend.admin.dto.UserStats;
import backend.admin.services.AdminService;
import backend.users.entities.UserEntity;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import java.util.List;

/**
 * The admin queries.
 */
@DgsComponent
public class AdminQueries {

  private final AdminService adminService;

  /**
   * Constructor for the admin queries.
   *
   * @param adminService the admin service the queries use.
   */
  public AdminQueries(AdminService adminService) {
    this.adminService = adminService;
  }

  /**
   * A query to get the user statistics.
   *
   * @return the user statistics.
   */
  @DgsQuery
  public UserStats getUserStats() {
    return adminService.getUserStats();
  }

  /**
   * A query to get a list of all the users.
   *
   * @return the list of users.
   */
  @DgsQuery
  public List<UserEntity> getAllUsers() {
    return adminService.getAllUsers();
  }

  /**
   * A query to get a list of all the admins.
   *
   * @return the list of admins.
   */
  @DgsQuery
  public List<Admin> getAllAdmins() {
    return adminService.getAllAdmins();
  }

  /**
   * A query to get the current admin.
   *
   * @return the current admin.
   */
  @DgsQuery
  public Admin getCurrentAdmin() {
    return adminService.getCurrentAdmin();
  }
}
