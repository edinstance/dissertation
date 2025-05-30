package backend.users.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for user billing input.
 */
@Getter
@Setter
public class UserBillingInput {

  /**
   * The user's id.
   */
  private String userId;

  /**
   * The users account id.
   */
  private String accountId;

  /**
   * The users customer id.
   */
  private String customerId;

  /**
   * Default constructor.
   */
  public UserBillingInput() {
  }

  /**
   * Constructor with values and a user id.
   *
   * @param userId     the user id.
   * @param accountId  the account id.
   * @param customerId the customer id.
   */
  public UserBillingInput(String userId,
                          String accountId, String customerId) {
    this.userId = userId;
    this.accountId = accountId;
    this.customerId = customerId;
  }

  /**
   * Constructor with values and no user id.
   *
   * @param accountId  the account id.
   * @param customerId the customer id.
   */
  public UserBillingInput(String accountId, String customerId) {
    this.accountId = accountId;
    this.customerId = customerId;
  }
}
