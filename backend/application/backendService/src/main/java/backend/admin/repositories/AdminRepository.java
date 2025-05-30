package backend.admin.repositories;


import backend.admin.entities.AdminEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for managing admin entities.
 */
public interface AdminRepository extends JpaRepository<AdminEntity, UUID> {

  /**
   * Function for creating an admin.
   *
   * @param userId  the id of the user to promote to an admin.
   * @param adminId the id of the admin performing this operation.
   */
  @Modifying
  @Query(value = "CALL create_admin(:userId, :adminId)",
          nativeQuery = true)
  @Transactional
  void createAdmin(
          @Param("userId") UUID userId,
          @Param("adminId") UUID adminId
  );

  /**
   * Function for making an admin a super admin.
   *
   * @param userId  the id of the admin to promote to a super admin.
   * @param adminId the id of the admin performing this operation.
   */
  @Modifying
  @Query(value = "CALL make_admin_super_admin(:userId, :adminId)",
          nativeQuery = true)
  @Transactional
  void makeAdminSuperAdmin(
          @Param("userId") UUID userId,
          @Param("adminId") UUID adminId
  );

  /**
   * Function for deactivating an admin.
   *
   * @param userId  the id of the admin to deactivate.
   * @param adminId the id of the admin performing this operation.
   */
  @Modifying
  @Query(value = "CALL deactivate_admin(:userId, :adminId)",
          nativeQuery = true)
  @Transactional
  void deactivateAdmin(
          @Param("userId") UUID userId,
          @Param("adminId") UUID adminId
  );
}