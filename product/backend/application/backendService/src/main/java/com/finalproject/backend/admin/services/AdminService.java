package com.finalproject.backend.admin.services;

import com.finalproject.backend.admin.dto.UserStats;
import com.finalproject.backend.admin.entities.AdminEntity;
import com.finalproject.backend.admin.repositories.AdminRepository;
import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.common.exceptions.UnauthorisedException;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.finalproject.backend.permissions.authorizers.AdminAuthorizer;
import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.AdminViewTypes;
import com.finalproject.backend.permissions.types.GrantType;
import com.finalproject.backend.permissions.types.Resources;
import com.finalproject.backend.users.entities.UserEntity;
import com.finalproject.backend.users.repositories.UserRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service class for Admin data.
 */
@Service
public class AdminService {

  /**
   * Repository for accessing user data.
   */
  private final UserRepository userRepository;

  /**
   * Authorizer for admin permissions.
   */
  private final AdminAuthorizer adminAuthorizer;
  private final AuthHelpers authHelpers;
  private final AdminRepository adminRepository;

  /**
   * Constructs an Admin Service with the specified UserRepository.
   *
   * @param inputUserRepository The repository for accessing user information.
   * @param inputAdminAuthorizer The authorizer to use.
   */
  @Autowired
  public AdminService(UserRepository inputUserRepository,
                      AdminAuthorizer inputAdminAuthorizer, AuthHelpers authHelpers,
                      AdminRepository adminRepository) {
    this.userRepository = inputUserRepository;
    this.adminAuthorizer = inputAdminAuthorizer;
    this.authHelpers = authHelpers;
    this.adminRepository = adminRepository;
  }

  /**
   * A function to get all the user statistics from the repository.
   *
   * @return the user statistics.
   */
  public UserStats getUserStats() {

    UUID currentUserId = authHelpers.getCurrentUserId();
    if (!adminAuthorizer.authorize(
            currentUserId,
            Resources.USERS,
            Actions.READ,
            GrantType.GRANT,
            AdminViewTypes.ALL)) {
      AppLogger.warn("User does not have permission to view user stats");
      throw new UnauthorisedException("User does not have permission to view user stats");
    }

    List<Object[]> results = userRepository.getAdminUserStats();

    if (results != null && !results.isEmpty()) {
      Object[] row = results.getFirst();

      long total = ((Number) row[0]).longValue();
      long newUserTotal = ((Number) row[1]).longValue();
      long deletedUserTotal = ((Number) row[2]).longValue();

      return new UserStats(total, newUserTotal, deletedUserTotal);
    } else {
      AppLogger.error("No user information found");
      return new UserStats(0, 0, 0);
    }
  }

  /**
   * A function to get all the users from the user repository.
   *
   * @return the list of users.
   */
  public List<UserEntity> getAllUsers() {
    UUID currentUserId = authHelpers.getCurrentUserId();
    if (!adminAuthorizer.authorize(
            currentUserId,
            Resources.USERS,
            Actions.READ,
            GrantType.GRANT,
            AdminViewTypes.ALL)) {
      AppLogger.warn("User does not have permission to view user stats");
      throw new UnauthorisedException("User does not have permission to view user stats");
    }

    return userRepository.findAll();
  }

  /**
   * A function to create a new admin.
   *
   * @param userId the id of the user to turn into an admin.
   * @return the new admin.
   */
  public AdminEntity createAdmin(final UUID userId) {

    UUID currentAdminId = authHelpers.getCurrentUserId();
    if (!adminAuthorizer.authorize(
            currentAdminId,
            Resources.ADMINS,
            Actions.CREATE,
            GrantType.GRANT,
            AdminViewTypes.ALL)) {
      AppLogger.warn("Admin does not have permission to create new admins");
      throw new UnauthorisedException("Admin does not have permission to create new admins");
    }

    AppLogger.info("Admin " + currentAdminId + " promoting user " + userId + " to admin");
    return adminRepository.createAdmin(currentAdminId, userId);
  }
}