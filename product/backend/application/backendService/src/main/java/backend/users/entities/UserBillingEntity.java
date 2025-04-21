package backend.users.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents user billing entity.
 */
@Entity
@Getter
@Setter
@Table(name = "user_billing")
public class UserBillingEntity {

  /**
   * The user's id.
   */
  @Id
  @Column(unique = true, nullable = false, name = "user_id")
  private UUID userId;

  /**
   * The user's account id.
   */
  @Column(name = "account_id")
  private String accountId;

  /**
   * The user's customer id.
   */
  @Column(name = "customer_id")
  private String customerId;


  /**
   * Default constructor.
   */
  public UserBillingEntity() {
  }

  public UserBillingEntity(final UUID userId,
                           final String accountId, final String customerId) {
    this.userId = userId;
    this.accountId = accountId;
    this.customerId = customerId;
  }
}