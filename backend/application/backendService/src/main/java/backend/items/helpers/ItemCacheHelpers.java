package backend.items.helpers;

import backend.common.config.logging.AppLogger;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * This class contains helpers for interacting with the item cache.
 */
@Component
public class ItemCacheHelpers {


  /**
   * Pool for accessing redis.
   */
  private final JedisPool jedisPool;


  /**
   * Constructor for the cache helper.
   *
   * @param jedisPool The jedis pool for the helper to use.
   */
  @Autowired
  public ItemCacheHelpers(JedisPool jedisPool) {
    this.jedisPool = jedisPool;
  }


  /**
   * This function invalidated a users items in the cache.
   *
   * @param userId The userId of the items to invalidate.
   */
  public void invalidateUserItems(final UUID userId) {
    try (Jedis jedis = jedisPool.getResource()) {
      String pattern = "user:" + userId + ":items:page:*";

      AppLogger.info("Invalidating all user items with pattern: " + pattern);

      Set<String> keys = jedis.keys(pattern);

      if (!keys.isEmpty()) {
        jedis.del(keys.toArray(new String[0]));
        AppLogger.info("Invalidated " + keys.size() + " cache entries for user: " + userId);
      } else {
        AppLogger.info("No cache entries found to invalidate for user: " + userId);
      }
    }
  }

}

