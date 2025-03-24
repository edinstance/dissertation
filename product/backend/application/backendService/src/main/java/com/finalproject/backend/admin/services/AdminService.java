package com.finalproject.backend.admin.services;

import com.finalproject.backend.admin.dto.UserStats;
import com.finalproject.backend.common.Exceptions.UnauthorisedException;
import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.finalproject.backend.permissions.authorizers.AdminAuthorizer;
import com.finalproject.backend.permissions.types.*;
import com.finalproject.backend.users.entities.UserEntity;
import com.finalproject.backend.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


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

  /**
   * Constructs an Admin Service with the specified UserRepository.
   *
   * @param inputUserRepository The repository for accessing user information.
   * @param inputAdminAuthorizer The authorizer to use.
   */
  @Autowired
  public AdminService(UserRepository inputUserRepository, AdminAuthorizer inputAdminAuthorizer, AuthHelpers authHelpers) {
    this.userRepository = inputUserRepository;
    this.adminAuthorizer = inputAdminAuthorizer;
    this.authHelpers = authHelpers;
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

}