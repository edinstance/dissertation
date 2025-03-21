package com.finalproject.backend.admin.services;

import com.finalproject.backend.admin.dto.UserStats;
import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.users.entities.UserEntity;
import com.finalproject.backend.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
   * Constructs an Admin Service with the specified UserRepository.
   *
   * @param inputUserRepository The repository for accessing user information.
   */
  @Autowired
  public AdminService( UserRepository inputUserRepository) {
    this.userRepository = inputUserRepository;
  }


  public UserStats getUserStats(){
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

  public List<UserEntity> getAllUsers() {
    return userRepository.findAll();
  }

}