package backend.common.config.kafka;

/**
 * Class that contains the kafka topics.
 */
public class KafkaTopics {
  public static final String CLOSED_AUCTION_TOPIC = "ClosedAuctions";
  public static final String BID_TOPIC = "Bids";

  /**
   * Private constructor to prevent instantiation.
   */
  private KafkaTopics() {
  }

}
