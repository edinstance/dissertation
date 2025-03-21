package com.finalproject.backend.users.repositories;

import com.finalproject.backend.users.entities.UserEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for managing user entities.
 */
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

  /**
   * This query deletes a user.
   *
   * @param userId the userId of the user to delete.
   */
  @Modifying
  @Query(value = "CALL delete_user(:userId)",
          nativeQuery = true)
  @Transactional
  void deleteUser(@Param("userId") UUID userId);


  /**
   * This query gets the stats about users.
   *
   * @return the UserStats as an object.
   */
  @Query(value = "SELECT * FROM get_admin_user_stats();",
          nativeQuery = true)
  List<Object[]> getAdminUserStats();
}
