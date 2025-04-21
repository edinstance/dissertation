package backend.users.repositories;

import backend.users.entities.UserBillingEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for user billing entities.
 */
public interface UserBillingRepository extends JpaRepository<UserBillingEntity, UUID> {

  /**
   * This query creates or updates the user billing.
   *
   * @param userId     the users billing to update.
   * @param accountId  the account id.
   * @param customerId the customer id.
   */
  @Modifying
  @Query(value = "SELECT * FROM insert_or_update_user_billing(" +
          ":userId, :accountId, :customerId)",
          nativeQuery = true)
  UserBillingEntity saveUserBilling(
          @Param("userId") UUID userId,
          @Param("accountId") String accountId,
          @Param("customerId") String customerId
  );
}
