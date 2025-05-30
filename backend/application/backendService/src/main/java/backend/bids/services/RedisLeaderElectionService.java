package backend.bids.services;

import backend.common.config.logging.AppLogger;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

/**
 * This service is for redis leader elections.
 */
@Service
public class RedisLeaderElectionService {

  /**
   * Date formatter.
   */
  private static final DateTimeFormatter FORMATTER =
          DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  /**
   * The key of the redis leader.
   */
  private static final String LEADER_KEY = "auction:service:leader";

  /**
   * How long the leader stays the leader.
   */
  private static final int LEADER_EXPIRY_SECONDS = 30;

  /**
   * The id of the instance.
   */
  @Value("${spring.application.instance-id:#{T(java.util.UUID).randomUUID().toString()}}")
  private String instanceId;

  /**
   * The jedis pool to use.
   */
  private final JedisPool jedisPool;

  /**
   * If the instance is the leader.
   */
  private final AtomicBoolean isLeader = new AtomicBoolean(false);

  /**
   * Constructor.
   *
   * @param jedisPool the jedis pool to use.
   */
  @Autowired
  public RedisLeaderElectionService(JedisPool jedisPool) {
    this.jedisPool = jedisPool;
  }

  /**
   * Scheduled task to attempt leadership acquisition or renewal.
   * Runs at a fixed rate of 1/3 of the expiry time to ensure
   * the leader key doesn't expire while the instance is still active.
   * Using @Scheduled eliminates the need for a busy-waiting loop with Thread.sleep()
   */
  @Scheduled(fixedRate = LEADER_EXPIRY_SECONDS * 1000 / 3)
  public void leadershipHeartbeat() {
    attemptToAcquireOrRefreshLeadership();
  }

  /**
   * Try to acquire or renew leadership using Redis SET NX EX.
   */
  private void attemptToAcquireOrRefreshLeadership() {
    try (Jedis jedis = jedisPool.getResource()) {
      LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
      String timestamp = now.format(FORMATTER);
      String hostInfo = getHostInfo();

      AppLogger.info("Attempting to acquire leadership for host: " + hostInfo);
      if (isLeader()) {
        // Already the leader, try to extend the lease
        String currentLeader = jedis.get(LEADER_KEY);
        if (instanceId.equals(currentLeader)) {
          // Still the leader, reset expiry
          jedis.expire(LEADER_KEY, LEADER_EXPIRY_SECONDS);
          AppLogger.info("Instance {} leadership renewed at {}", instanceId, timestamp);
        } else {
          // Lost leadership
          isLeader.set(false);
          AppLogger.info("Instance {} lost leadership at {}", instanceId, timestamp);
        }
      } else {
        // Try to become the leader using SET NX EX (only set if not exists with expiry)
        String result = jedis.set(LEADER_KEY, instanceId,
                SetParams.setParams().nx().ex(LEADER_EXPIRY_SECONDS));

        if ("OK".equals(result)) {
          isLeader.set(true);
          AppLogger.info("Instance {} acquired leadership at {} on {}",
                  instanceId, timestamp, hostInfo);
        }
      }
    } catch (Exception e) {
      AppLogger.error("Error in leader election: {} at {}",
              e.getMessage(), LocalDateTime.now(ZoneOffset.UTC).format(FORMATTER));

      // On error, assume not the leader for safety
      if (isLeader()) {
        isLeader.set(false);
        AppLogger.warn("Instance {} relinquishing leadership due to error", instanceId);
      }
    }
  }

  /**
   * Function to get if the instance is leader.
   */
  public boolean isLeader() {
    return isLeader.get();
  }

  /**
   * On shutdown, release leadership if the instance has it.
   */
  public void shutdown() {
    if (isLeader.get()) {
      try (Jedis jedis = jedisPool.getResource()) {
        String currentLeader = jedis.get(LEADER_KEY);
        if (instanceId.equals(currentLeader)) {
          jedis.del(LEADER_KEY);
          AppLogger.info("Instance {} released leadership at shutdown at {}",
                  instanceId, LocalDateTime.now(ZoneOffset.UTC).format(FORMATTER));
        }
      } catch (Exception e) {
        AppLogger.error("Error releasing leadership on shutdown: {}", e.getMessage());
      }
    }
  }

  /**
   * A function to get the host information.
   *
   * @return the hostname.
   */
  public String getHostInfo() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (Exception e) {
      return "unknown-host";
    }
  }
}