package com.finalproject.backend.users.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.finalproject.backend.users.entities.UserEntity;
import com.finalproject.backend.users.repositories.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

/**
 * Service class for managing User entities.
 */
@Service
public class UserService {

  /**
   * Repository for accessing user data.
   */
  private final UserRepository userRepository;

  /**
   * Pool for accessing redis.
   */
  private final JedisPool jedisPool;

  /**
   * Object mapper for mapping to json.
   */
  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * The auth helpers to use.
   */
  private final AuthHelpers authHelpers;

  /**
   * Constructs a UserService with the specified UserRepository.
   *
   * @param inputUserRepository The repository for accessing User entities.
   * @param inputJedisPool      The jedis pool to interact with.
   */
  @Autowired
  public UserService(final UserRepository inputUserRepository,
                     final JedisPool inputJedisPool, final AuthHelpers inputAuthHelpers) {
    this.userRepository = inputUserRepository;
    this.jedisPool = inputJedisPool;
    this.authHelpers = inputAuthHelpers;
  }

  /**
   * Creates a new user.
   *
   * @param newUser the user to be created.
   * @return the created user.
   * @throws IllegalArgumentException error if duplicated UUId.
   * @throws RuntimeException         if json processing fails.
   */
  public UserEntity createUser(final UserEntity newUser) {
    Optional<UserEntity> existingUser = userRepository.findById(newUser.getId());

    if (existingUser.isPresent()) {
      AppLogger.error("User with id " + newUser.getId() + " already exists");
      throw new IllegalArgumentException("User with UUID "
              + newUser.getId() + " already exists.");
    }

    try (Jedis jedis = jedisPool.getResource()) {
      jedis.set("user:" + newUser.getId().toString(),
              objectMapper.writeValueAsString(newUser),
              SetParams.setParams().ex(300));

    } catch (JsonProcessingException e) {
      AppLogger.error("Error while creating user", e);
      throw new RuntimeException("Error processing JSON", e);
    }

    return userRepository.save(newUser);
  }

  /**
   * Retrieves a user entity by its ID.
   *
   * @param id The ID of the user.
   * @return The user entity.
   */
  public UserEntity getUserById(UUID id) {
    try (Jedis jedis = jedisPool.getResource()) {

      String key = "user:" + id.toString();
      String cachedValueString = jedis.get(key);

      if (cachedValueString != null) {
        AppLogger.info("User with id " + id + " found in cache");
        jedis.expire(key, 300);
        return objectMapper.readValue(cachedValueString, UserEntity.class);
      }

      UserEntity user = userRepository.findById(id).orElse(null);
      AppLogger.info("User with id " + id + " found in the database");
      if (user == null || user.getIsDeleted()) {
        return null;
      }

      jedis.set(key, objectMapper.writeValueAsString(user),
              SetParams.setParams().ex(300));

      return user;

    } catch (JsonProcessingException e) {
      AppLogger.error("Error while getting user", e);
      throw new RuntimeException(e);
    }
  }

  /**
   * This deletes a user by their id.
   */
  public Boolean deleteUser() {
    try (Jedis jedis = jedisPool.getResource()) {
      UUID userId = authHelpers.getCurrentUserId();
      String key = "user:" + userId.toString();
      jedis.del(key);

      userRepository.deleteUser(userId);
      AppLogger.info("User with id " + userId + " deleted");
      return true;
    } catch (Exception e) {
      AppLogger.error("Error while deleting user", e);
      return false;
    }
  }

  /**
   * This deactivates a user by their id.
   */
  public void deactivateUser(final UUID userId) {
    userRepository.deactivateUser(userId);
    AppLogger.info("User with id " + userId + " deactivated");
  }
}

