package backend.bids.services;

import backend.bids.dto.CreateBidDto;
import backend.bids.helpers.BidCacheHelpers;
import backend.bids.helpers.BidHelpers;
import backend.bids.helpers.BidKafkaHelpers;
import backend.common.config.logging.AppLogger;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

/**
 * A service class for the bid mutations.
 */
@Service
public class BidMutationService {

  /**
   * The bid helpers to use.
   */
  private final BidHelpers bidHelpers;

  /**
   * The bid cache helpers to use.
   */
  private final BidCacheHelpers bidCacheHelpers;

  /**
   * The bid kafka helpers to use.
   */
  private final BidKafkaHelpers bidKafkaHelpers;

  /**
   * Constructor for the mutation service.
   *
   * @param bidHelpers the bid helpers to use.
   * @param bidCacheHelpers the bid cache helpers to use.
   * @param bidKafkaHelpers the bid kafka helpers to use.
   */
  @Autowired
  public BidMutationService(BidHelpers bidHelpers,
                            BidCacheHelpers bidCacheHelpers, BidKafkaHelpers bidKafkaHelpers) {
    this.bidKafkaHelpers = bidKafkaHelpers;
    this.bidHelpers = bidHelpers;
    this.bidCacheHelpers = bidCacheHelpers;
  }

  /**
   * Creates a new bid if it's valid.
   *
   * @param bidDto the bid to create.
   *
   * @return true if bid was accepted, false if rejected due to business rules.
   */
  public boolean createBid(CreateBidDto bidDto) {
    if (bidDto == null || bidDto.getItemId() == null || bidDto.getAmount() == null) {
      AppLogger.error("createBid called with null parameters");
      return false;
    }

    BigDecimal currentHighestBid;

    try {
      currentHighestBid = bidHelpers.getCurrentHighestBid(
              bidDto.getItemId());

      if (currentHighestBid.compareTo(bidDto.getAmount()) >= 0) {
        AppLogger.warn(
                "Bid amount {} is less than or equal to the current highest bid {}. Bid rejected.",
                bidDto.getAmount(),
                currentHighestBid);
        return false;
      }
    } catch (Exception e) {
      AppLogger.error("Error occurred while creating new bid {}", e);
      return false;
    }

    try {
      CompletableFuture<SendResult<String, CreateBidDto>> resultFuture = new CompletableFuture<>();

      bidKafkaHelpers.attemptSend(bidDto, 5, 100, 0, resultFuture);

      bidCacheHelpers.updateCachedHighestBid(bidDto.getBidId(), bidDto.getAmount());

      return true;
    } catch (Exception e) {
      AppLogger.error("Error publishing bid to Kafka: {}", e);
      return false;
    }
  }

}