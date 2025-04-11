package com.finalproject.backend.admin.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.admin.dto.Admin;
import com.finalproject.backend.admin.dto.UserStats;
import com.finalproject.backend.admin.entities.AdminEntity;
import com.finalproject.backend.admin.mappers.AdminMapper;
import com.finalproject.backend.admin.repositories.AdminRepository;
import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.common.exceptions.UnauthorisedException;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.finalproject.backend.permissions.authorizers.AdminAuthorizer;
import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.AdminViewTypes;
import com.finalproject.backend.permissions.types.Resources;
import com.finalproject.backend.users.entities.UserEntity;
import com.finalproject.backend.users.repositories.UserRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


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

  /**
   * The auth helpers to use.
   */
  private final AuthHelpers authHelpers;

  /**
   * Repository for accessing admin data.
   */
  private final AdminRepository adminRepository;

  /**
   * Pool for accessing redis.
   */
  private final JedisPool jedisPool;

  /**
   * Object mapper for mapping to json.
   */
  private final ObjectMapper objectMapper;


  /**
   * Constructs an Admin Service with the specified UserRepository.
   *
   * @param inputUserRepository  The repository for accessing user information.
   * @param inputAdminAuthorizer The authorizer to use.
   * @param authHelpers          The auth helpers to use.
   * @param adminRepository      The repository for accessing admin information.
   * @param jedisPool            The pool for accessing redis.
   */
  @Autowired
  public AdminService(UserRepository inputUserRepository,
                      AdminAuthorizer inputAdminAuthorizer, AuthHelpers authHelpers,
                      AdminRepository adminRepository, JedisPool jedisPool,
                      ObjectMapper objectMapper) {
    this.userRepository = inputUserRepository;
    this.adminAuthorizer = inputAdminAuthorizer;
    this.authHelpers = authHelpers;
    this.adminRepository = adminRepository;
    this.jedisPool = jedisPool;
    this.objectMapper = objectMapper;
  }

  /**
   * A function to get the current admin.
   *
   * @return the current admin.
   */
  public Admin getCurrentAdmin() {
    UUID currentUserId = authHelpers.getCurrentUserId();

    if (currentUserId == null) {
      AppLogger.info("No user id found");
      return null;
    }
    String cacheKey = "admin:" + currentUserId;

    try (Jedis jedis = jedisPool.getResource()) {

      String cachedAdmin = jedis.get(cacheKey);

      if (cachedAdmin != null) {
        return objectMapper.readValue(cachedAdmin, Admin.class);
      }

      AdminEntity adminEntity = adminRepository.findById(currentUserId).orElse(null);

      if (adminEntity != null) {
        Admin admin = AdminMapper.mapAdminEntityToAdmin(adminEntity);

        jedis.setex(cacheKey, 600, objectMapper.writeValueAsString(admin));

        return admin;
      }
      return null;
    } catch (Exception e) {
      AppLogger.error("Error getting current admin", e);
      return null;
    }
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
            AdminViewTypes.ALL)) {
      AppLogger.warn("Admin does not have permission to view user data");
      throw new UnauthorisedException("Admin does not have permission to view user data");
    }

    return userRepository.findAll();
  }

  /**
   * This method gets all the admins.
   *
   * @return a list of all the admins.
   */
  public List<Admin> getAllAdmins() {
    UUID currentUserId = authHelpers.getCurrentUserId();
    if (!adminAuthorizer.authorize(
            currentUserId,
            Resources.ADMINS,
            Actions.READ,
            AdminViewTypes.ALL)) {
      AppLogger.warn("Admin does not have permission to view admin data");
      throw new UnauthorisedException("Admin does not have permission to view admin data");
    }

    return AdminMapper.mapAdminEntityListToAdmins(adminRepository.findAll());
  }

  /**
   * A function to create a new admin.
   *
   * @param userId the id of the user to turn into an admin.
   *
   * @return the new admin.
   */
  public Boolean createAdmin(final UUID userId) {

    UUID currentAdminId = authHelpers.getCurrentUserId();
    if (!adminAuthorizer.authorize(
            currentAdminId,
            Resources.ADMINS,
            Actions.CREATE,
            AdminViewTypes.ALL)) {
      AppLogger.warn("Admin does not have permission to create new admins");
      throw new UnauthorisedException("Admin does not have permission to create new admins");
    }

    AppLogger.info("Admin " + currentAdminId + " promoting user " + userId + " to admin");
    adminRepository.createAdmin(userId, currentAdminId);

    try (Jedis jedis = jedisPool.getResource()) {
      adminRepository.findById(currentAdminId).ifPresent(admin -> {
        try {
          String cacheKey = "admin:" + currentAdminId;
          jedis.setex(cacheKey, 3600, objectMapper.writeValueAsString(admin));
        } catch (JsonProcessingException e) {
          AppLogger.error("Error writing admin to cache", e);
        }
      });
    } catch (Exception e) {
      AppLogger.error("Error with admin cache", e);
    }
    return true;
  }

  /**
   * This function promotes an admin to a super admin.
   *
   * @param userId the id of the admin to promote.
   *
   * @return true if the promotion was successful.
   */
  public Boolean promoteAdminToSuperUser(final UUID userId) {

    UUID currentAdminId = authHelpers.getCurrentUserId();
    if (!getCurrentAdmin().isSuperAdmin()) {
      AppLogger.warn("Admin does not have permission to create super admins");
      throw new UnauthorisedException("Admin does not have permission to create super admins");
    }

    AppLogger.info("Admin " + currentAdminId + " promoting user " + userId + " to super admin");
    adminRepository.makeAdminSuperAdmin(userId, currentAdminId);

    try (Jedis jedis = jedisPool.getResource()) {
      jedis.del("admin:" + userId);
    }

    return true;
  }

  /**
   * This function deactivates an admin.
   *
   * @param userId the id of the admin to deactivate.
   *
   * @return true if the promotion was successful.
   */
  public Boolean deactivateAdmin(final UUID userId) {

    UUID currentAdminId = authHelpers.getCurrentUserId();
    if (!adminAuthorizer.authorize(
            currentAdminId,
            Resources.ADMINS,
            Actions.DELETE,
            AdminViewTypes.ALL)) {
      AppLogger.warn("Admin does not have permission to deactivate admins");
      throw new UnauthorisedException("Admin does not have permission to deactivate admins");
    }

    AppLogger.info("Admin " + currentAdminId + " deactivating user " + userId);
    adminRepository.deactivateAdmin(userId, currentAdminId);

    try (Jedis jedis = jedisPool.getResource()) {
      jedis.del("admin:" + userId);
    }

    return true;
  }
}