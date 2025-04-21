package backend.users.mutations;

import backend.common.dto.MutationResponse;
import backend.users.dto.UserBillingInput;
import backend.users.services.UserBillingService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * GraphQL mutations for user-billing-related operations.
 */
@DgsComponent
public class UserBillingMutations {

  /**
   * The service the mutation will interact with.
   */
  private final UserBillingService userBillingService;

  /**
   * Constructor.
   *
   * @param userBillingService the service to use.
   */
  @Autowired
  public UserBillingMutations(UserBillingService userBillingService) {
    this.userBillingService = userBillingService;
  }

  /**
   * This mutation saves the user billing information.
   *
   * @param userBillingInput the billing information.
   *
   * @return the user billing entity.
   */
  @DgsMutation
  public MutationResponse saveUserBilling(@InputArgument UserBillingInput userBillingInput) {
    if( userBillingService.saveUserBilling(userBillingInput)){
      return new MutationResponse(true, "User billing saved");
    }
    return new MutationResponse(false, "User billing not saved");
  }

}
