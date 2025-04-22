package backend.common.config.kafka;

/**
 * Class that contains the kafka groups.
 */
public class KafkaGroups {
  public static final String CLOSED_AUCTION_GROUP = "ClosedAuctions";
  public static final String BID_GROUP = "Bids";

  /**
   * Private constructor to prevent instantiation.
   */
  private KafkaGroups() {
  }

}
