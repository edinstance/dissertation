package backend.users.services;

import backend.common.config.logging.AppLogger;
import backend.common.helpers.AuthHelpers;
import backend.users.entities.UserDetailsEntity;
import backend.users.entities.UserEntity;
import backend.users.helpers.UserHelpers;
import backend.users.repositories.UserDetailsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

/**
 * Service class for managing User entities.
 */
@Service
public class UserDetailsService {

  /**
   * Repository for accessing user details data.
   */
  private final UserDetailsRepository userDetailsRepository;

  /**
   * User helpers.
   */
  private final UserHelpers userHelpers;

  /**
   * Auth helpers.
   */
  private final AuthHelpers authHelpers;

  /**
   * Pool for accessing redis.
   */
  private final JedisPool jedisPool;

  /**
   * Object mapper for mapping to json.
   */
  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Constructs a UserService with the specified UserRepository and UserDetails Repository.
   *
   * @param inputUserDetailsRepository The repository for accessing User details entities.
   * @param inputAuthHelpers           The authHelper for this service.
   * @param inputUserHelpers           The userHelper for this service.
   * @param inputJedisPool             The jedis pool to interact with.
   */
  @Autowired
  public UserDetailsService(final UserDetailsRepository inputUserDetailsRepository,
                            UserHelpers inputUserHelpers, AuthHelpers inputAuthHelpers,
                            JedisPool inputJedisPool) {
    this.userDetailsRepository = inputUserDetailsRepository;
    this.userHelpers = inputUserHelpers;
    this.authHelpers = inputAuthHelpers;
    this.jedisPool = inputJedisPool;
  }

  /**
   * Updates or creates the user details.
   *
   * @param newDetails the details to be edited.
   *
   * @throws IllegalArgumentException error if duplicated UUId.
   */
  @Transactional
  public UserEntity saveUserDetails(final UserDetailsEntity newDetails) {
    userDetailsRepository.saveUserDetails(newDetails.getId(),
            newDetails.getContactNumber(), newDetails.getHouseName(),
            newDetails.getAddressStreet(),
            newDetails.getAddressCity(), newDetails.getAddressCounty(),
            newDetails.getAddressPostcode()
    );
    UserEntity user = userHelpers.getUserById(newDetails.getId());
    user.setUserDetailsEntity(newDetails);

    AppLogger.info("Saved user details: " + user);

    try (Jedis jedis = jedisPool.getResource()) {
      jedis.set("user:" + user.getId().toString(),
              objectMapper.writeValueAsString(user),
              SetParams.setParams().ex(300));
    } catch (JsonProcessingException e) {
      AppLogger.error("Error while saving user details: " + e);
      throw new RuntimeException("Error processing JSON", e);
    }

    return user;
  }

  /**
   * This function checks if the user has created details.
   *
   * @return if the details exist.
   */
  public boolean checkCurrentUserDetailsExist() {
    return userDetailsRepository.existsById(authHelpers.getCurrentUserId());
  }

}