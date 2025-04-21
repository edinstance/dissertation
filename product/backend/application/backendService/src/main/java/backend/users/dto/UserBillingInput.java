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
   * The users account id.
   */
  private String accountId;

  /**
   * The users customer id.
   */
  private String customerId;

  /**
   * Default constructor
   */
  public UserBillingInput() {
  }

  /**
   * Constructor with values.
   *
   * @param accountId  the account id.
   * @param customerId the customer id.
   */
  public UserBillingInput(String accountId, String customerId) {
    this.accountId = accountId;
    this.customerId = customerId;
  }
}
