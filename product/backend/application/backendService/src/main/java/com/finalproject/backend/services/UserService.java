package com.finalproject.backend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.repositories.UserRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

/**
 * Service class for managing User entities.
 */
@Service
@Slf4j
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
   * Constructs a UserService with the specified UserRepository.
   *
   * @param inputUserRepository The repository for accessing User entities.
   * @param inputJedisPool The jedis pool to interact with.
   */
  @Autowired
  public UserService(final UserRepository inputUserRepository,
                     final JedisPool inputJedisPool) {
    this.userRepository = inputUserRepository;
    this.jedisPool = inputJedisPool;
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
      throw new IllegalArgumentException("User with UUID "
              + newUser.getId() + " already exists.");
    }

    try (Jedis jedis = jedisPool.getResource()) {
      jedis.set("user:" + newUser.getId().toString(),
              objectMapper.writeValueAsString(newUser),
              SetParams.setParams().ex(300));

    } catch (JsonProcessingException e) {
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
      String cachedString = jedis.get(key);

      if (cachedString != null) {
        jedis.expire(key, 300);
        return objectMapper.readValue(cachedString, UserEntity.class);
      }

      UserEntity user = userRepository.findById(id).orElse(null);

      if ( user != null ) {
        jedis.set(key, objectMapper.writeValueAsString(user),
                SetParams.setParams().ex(300));
      }

      return user;

    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
