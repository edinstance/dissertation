package backend.users.queries;

import backend.users.entities.UserBillingEntity;
import backend.users.services.UserBillingService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This component contains the user billing queries.
 */
@DgsComponent
public class UserBillingQueries {

  private final UserBillingService userBillingService;

  /**
   * Constructor for the billing queries.
   *
   * @param userBillingService the billing service to use.
   */
  @Autowired
  public UserBillingQueries(UserBillingService userBillingService) {
    this.userBillingService = userBillingService;
  }

  /**
   * This query gets the users billing information.
   *
   * @return the users billing information.
   */
  @DgsQuery
  public UserBillingEntity getUserBilling() {
    return userBillingService.getUserBilling();
  }
}
