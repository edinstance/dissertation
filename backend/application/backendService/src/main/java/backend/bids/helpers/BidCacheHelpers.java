package backend.bids.helpers;

import backend.common.config.logging.AppLogger;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Cache helpers for bidding.
 */
@Component
public class BidCacheHelpers {

  /**
   * Format of the item current bid cache key.
   */
  public static final String ITEM_CURRENT_BID_KEY_FORMAT = "item:%s:currentBidPrice";

  /**
   * How long items current bids in the cache should last.
   */
  public static final int ITEM_CURRENT_BID_CACHE_EXPIRY_SECONDS = 3600;

  /**
   * The jedis pool to use.
   */
  private final JedisPool jedisPool;

  /**
   * Constructor for the cache helpers.
   *
   * @param jedisPool the jedis pool to use.
   */
  @Autowired
  public BidCacheHelpers(JedisPool jedisPool) {
    this.jedisPool = jedisPool;
  }

  /**
   * A function to update the cached highest bids.
   *
   * @param itemId    the item id.
   * @param newAmount the new amount.
   */
  public void updateCachedHighestBid(UUID itemId, BigDecimal newAmount) {
    try (Jedis jedis = jedisPool.getResource()) {
      String cacheKey = String.format(ITEM_CURRENT_BID_KEY_FORMAT, itemId);

      AppLogger.info("Updating cached highest bid for item: " + itemId);
      jedis.setex(cacheKey, ITEM_CURRENT_BID_CACHE_EXPIRY_SECONDS, newAmount.toString());
    } catch (Exception e) {
      AppLogger.warn("Failed to update bid cache: {}", e.getMessage());
    }
  }
}
