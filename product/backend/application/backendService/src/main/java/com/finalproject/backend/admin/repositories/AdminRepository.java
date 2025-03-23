package com.finalproject.backend.admin.repositories;


import com.finalproject.backend.admin.entities.AdminEntity;
import com.finalproject.backend.users.entities.UserDetailsEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for managing admin entities.
 */
public interface AdminRepository extends JpaRepository<AdminEntity, UUID> {

  /**
   * Function for creating an admin.
   * @param userId the id of the user to promote to an admin.
   * @param adminId the id of the admin performing this operation.
   */
  @Modifying
  @Query(value = "PERFORM create_admin(:userId, :adminId)",
          nativeQuery = true)
  void createAdmin(
          @Param("userId") UUID userId,
          @Param("adminId") UUID adminId
  );
}
