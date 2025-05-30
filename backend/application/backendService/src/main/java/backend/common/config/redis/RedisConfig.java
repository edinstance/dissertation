package backend.common.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * This class contains the redis configuration.
 */
@Configuration
public class RedisConfig {

  /**
   * This value is the redis host ip.
   */
  @Value("${redis.host}")
  private String redisHost;

  /**
   * This value is for the redis port.
   */
  @Value("${redis.port}")
  private int redisPort;

  /**
   * This creates a jedis pool.
   *
   * @return the created pool.
   */
  @Bean
  public JedisPool jedisPool() {
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setJmxEnabled(false);
    poolConfig.setMaxTotal(20);
    poolConfig.setMaxIdle(10);
    poolConfig.setMinIdle(5);
    poolConfig.setBlockWhenExhausted(true);
    return new JedisPool(poolConfig, redisHost, redisPort);
  }
}
