package com.finalproject.backend.items.helpers;

import com.finalproject.backend.common.config.logging.AppLogger;
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
   * @param page The page of items to invalidate.
   */
  public void invalidateUserItems(final UUID userId, final int page) {
    try (Jedis jedis = jedisPool.getResource()) {
      String key = "user:" + userId + ":items:page:" + page;

      AppLogger.info("Invalidating: " + key);

      jedis.del(key);
    }
  }

}
