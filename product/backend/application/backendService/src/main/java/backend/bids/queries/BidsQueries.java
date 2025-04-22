package backend.bids.queries;

import backend.bids.dynamodb.BidsDynamoService;
import backend.common.dynamodb.tables.Bids;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This component contains the bid queries.
 */
@DgsComponent
public class BidsQueries {

  /**
   * The bid dynamo service to use.
   */
  private final BidsDynamoService bidsDynamoService;

  /**
   * Constructor for the bid queries.
   *
   * @param bidsDynamoService the dynamo service to use.
   */
  @Autowired
  public BidsQueries(BidsDynamoService bidsDynamoService) {
    this.bidsDynamoService = bidsDynamoService;
  }

  /**
   * This query gets a list of bids for an item id.
   *
   * @param itemId the id of the item.
   * @return the item's bids.
   */
  @DgsQuery
  public List<Bids> getItemBidsById(
          @InputArgument String itemId) {

    return bidsDynamoService.getItemBids(UUID.fromString(itemId));
  }
}
