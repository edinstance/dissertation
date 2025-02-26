package com.finalproject.backend.users.repositories;

import com.finalproject.backend.users.entities.UserDetailsEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for managing user details entities.
 */
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, UUID> {

  /**
   * This query creates or updates user details.
   *
   * @param userId The id of the user to update.
   * @param contactNumber The value of their contact number.
   * @param houseName The value of their house name.
   * @param addressStreet The value of their street.
   * @param addressCity The value of their city.
   * @param addressCounty The value of their county.
   * @param addressPostCode The value of their postcode .
   */
  @Modifying
  @Query(value = "CALL insert_or_update_user_details(:userId, :contactNumber,"
          + " :houseName, :addressStreet, :addressCity, :addressCounty,"
          + " :addressPostCode)",
          nativeQuery = true)
  void saveUserDetails(
          @Param("userId") UUID userId,
          @Param("contactNumber") String contactNumber,
          @Param("houseName") String houseName,
          @Param("addressStreet") String addressStreet,
          @Param("addressCity") String addressCity,
          @Param("addressCounty") String addressCounty,
          @Param("addressPostCode") String addressPostCode
  );
}
