package backend.bids.helpers;

import static backend.bids.helpers.BidCacheHelpers.ITEM_CURRENT_BID_CACHE_EXPIRY_SECONDS;
import static backend.bids.helpers.BidCacheHelpers.ITEM_CURRENT_BID_KEY_FORMAT;

import backend.bids.dynamodb.BidsDynamoService;
import backend.common.config.logging.AppLogger;
import backend.common.dynamodb.tables.Bids;
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
        jedis.expire(cacheKey, ITEM_CURRENT_BID_CACHE_EXPIRY_SECONDS);
        return new BigDecimal(itemPriceStr);
      } else {
        Bids mostRecentBid = bidsDynamoService.getMostRecentBid(itemId);
        if (mostRecentBid == null) {
          AppLogger.warn("No bids found in DynamoDB for item {}", itemId);
          jedis.setex(
                  cacheKey, ITEM_CURRENT_BID_CACHE_EXPIRY_SECONDS, BigDecimal.ZERO.toString());
          return BigDecimal.ZERO;
        }

        BigDecimal itemPrice = mostRecentBid.getAmount();

        if (itemPrice.compareTo(BigDecimal.ZERO) < 0) {
          itemPrice = BigDecimal.ZERO;
        }
        jedis.setex(
                cacheKey, ITEM_CURRENT_BID_CACHE_EXPIRY_SECONDS, itemPrice.toString());
        return itemPrice;
      }
    } catch (Exception e) {
      AppLogger.error("Error accessing data store: {}", e.getMessage());
      throw new RuntimeException("Failed to retrieve current bid price", e);
    }
  }

}
