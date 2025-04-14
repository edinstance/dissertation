package backend.bids.helpers;

import static backend.bids.helpers.BidCacheHelpers.ITEM_CURRENT_BID_CACHE_EXPIRY_SECONDS;
import static backend.bids.helpers.BidCacheHelpers.ITEM_CURRENT_BID_KEY_FORMAT;

import backend.bids.dynamodb.BidsDynamoService;
import backend.common.config.logging.AppLogger;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * These are helpers for the bids service.
 */
@Component
public class BidHelpers {

  /**
   * The jedis pool to use.
   */
  private final JedisPool jedisPool;

  /**
   * The dynamo service to use.
   */
  private final BidsDynamoService bidsDynamoService;


  /**
   * Constructor for the bid helpers.
   *
   * @param jedisPool         the jedis pool to use.
   * @param bidsDynamoService the dynamo service to use.
   */
  @Autowired
  public BidHelpers(JedisPool jedisPool,
                    BidsDynamoService bidsDynamoService) {
    this.jedisPool = jedisPool;
    this.bidsDynamoService = bidsDynamoService;
  }

  /**
   * This helper gets the current highest bid for an item.
   *
   * @param itemId the item to check.
   *
   * @return the price of the highest bid.
   */
  public BigDecimal getCurrentHighestBid(UUID itemId) {
    try (Jedis jedis = jedisPool.getResource()) {
      String cacheKey = String.format(ITEM_CURRENT_BID_KEY_FORMAT, itemId);
      String itemPriceStr = jedis.get(cacheKey);

      if (itemPriceStr != null) {
        return new BigDecimal(itemPriceStr);
      } else {
        BigDecimal itemPrice = bidsDynamoService.getMostRecentBid(itemId).getAmount();
        jedis.setex(cacheKey, ITEM_CURRENT_BID_CACHE_EXPIRY_SECONDS, itemPrice.toString());
        return itemPrice;
      }
    } catch (Exception e) {
      AppLogger.error("Error accessing data store: {}", e);
      throw new RuntimeException("Failed to retrieve current bid price", e);
    }
  }

}
