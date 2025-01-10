package com.finalproject.backend.repositories;

import com.finalproject.backend.entities.UserEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
  void deleteUser(@Param("userId") UUID userId);
}
