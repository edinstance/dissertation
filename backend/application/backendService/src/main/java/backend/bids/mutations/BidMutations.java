package backend.bids.mutations;

import backend.bids.dto.CreateBidDto;
import backend.bids.services.BidMutationService;
import backend.common.dto.MutationResponse;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class for the bid mutations.
 */
@DgsComponent
public class BidMutations {

  /**
   * the bid mutation service to use.
   */
  private final BidMutationService bidMutationService;

  /**
   * Constructor.
   *
   * @param bidMutationService the bid mutation service to use.
   */
  @Autowired
  public BidMutations(BidMutationService bidMutationService) {
    this.bidMutationService = bidMutationService;
  }

  /**
   * Mutation to submit a bid.
   *
   * @param bid the bid to submit.
   *
   * @return a response of if the bid was successful.
   */
  @DgsMutation
  public MutationResponse submitBid(@InputArgument final CreateBidDto bid) {
    boolean response = bidMutationService.createBid(bid);

    if (response) {
      return new MutationResponse(true, "Bid placed successfully.");
    }
    return new MutationResponse(false, "Error placing bid, please try again.");
  }
}
