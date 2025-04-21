package backend.users.services;

import backend.common.config.logging.AppLogger;
import backend.common.helpers.AuthHelpers;
import backend.users.dto.UserBillingInput;
import backend.users.entities.UserBillingEntity;
import backend.users.repositories.UserBillingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Service class for managing User billing entities.
 */
@Service
public class UserBillingService {

  /**
   * Repository to interact with.
   */
  private final UserBillingRepository userBillingRepository;

  /**
   * Jedis pool to use.
   */
  private final JedisPool jedisPool;

  /**
   * Auth helpers to use.
   */
  private final AuthHelpers authHelpers;

  /**
   * Object mapper to use.
   */
  private final ObjectMapper objectMapper;

  /**
   * Constructor for the user billing service.
   *
   * @param userBillingRepository repository to use.
   * @param jedisPool             jedis pool to use.
   * @param authHelpers           auth helpers to use.
   * @param objectMapper          object mapper to use.
   */
  @Autowired
  public UserBillingService(UserBillingRepository userBillingRepository,
                            JedisPool jedisPool, AuthHelpers authHelpers,
                            ObjectMapper objectMapper) {
    this.userBillingRepository = userBillingRepository;
    this.jedisPool = jedisPool;
    this.authHelpers = authHelpers;
    this.objectMapper = objectMapper;
  }

  /**
   * This function saves or created a user billing entity.
   *
   * @param userBillingInput the input.
   *
   * @return the new entity.
   */
  public UserBillingEntity saveUserBilling(
          final UserBillingInput userBillingInput) {
    UUID userId = authHelpers.getCurrentUserId();
    AppLogger.info(String.format("Saving user billing %s", userId));

    try (Jedis jedis = jedisPool.getResource()) {
      jedis.del("user:" + userId + ":billing");
    } catch (Exception e) {
      AppLogger.error("Error saving user billing", e);
    }
    return userBillingRepository.saveUserBilling(userId,
            userBillingInput.getAccountId(), userBillingInput.getCustomerId());
  }

  /**
   * This function gets the users billing information.
   *
   * @return the users billing information
   */
  public UserBillingEntity getUserBilling() {
    UUID userId = authHelpers.getCurrentUserId();

    try (Jedis jedis = jedisPool.getResource()) {
      String cachedBilling = jedis.get("user:" + userId + ":billing");

      if (cachedBilling != null) {
        AppLogger.info(String.format("Found user billing %s in cache", cachedBilling));

        return objectMapper.convertValue(cachedBilling, UserBillingEntity.class);
      }
      UserBillingEntity userBillingEntity = userBillingRepository.findById(userId).orElse(null);

      if (userBillingEntity != null) {
        AppLogger.info(String.format("Found user billing %s in Database", userId));
        jedis.setex("user:" + userId + ":billing", 3600,
                objectMapper.writeValueAsString(userBillingEntity));
        return userBillingEntity;
      }
      return null;
    } catch (Exception e) {
      AppLogger.error("Error getting user billing", e);
      return null;
    }
  }
}
