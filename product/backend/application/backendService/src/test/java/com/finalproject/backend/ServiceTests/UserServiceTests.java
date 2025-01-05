package com.finalproject.backend.ServiceTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.repositories.UserRepository;
import com.finalproject.backend.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

  @Mock
  private UserRepository userRepository;

  @Mock
  private JedisPool jedisPool;

  @Mock
  private Jedis jedis;

  @Mock
  private ObjectMapper objectMapper;

  @InjectMocks
  private UserService userService;

  @Test
  public void testCreateExistingUser() {
    UUID userId = UUID.randomUUID();
    UserEntity existingUser = new UserEntity(userId, "existing@test.com", "Existing User");

    when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

    UserEntity newUser = new UserEntity(userId, "new@test.com", "New User");

    assertThrows(IllegalArgumentException.class, () -> userService.createUser(newUser),
            "User with UUID " + newUser.getId() + " already exists.");
  }

  @Test
  public void testCreateNewUser() {
    UUID userId = UUID.randomUUID();
    UserEntity newUser = new UserEntity(userId, "new@test.com", "New User");

    when(userRepository.findById(userId)).thenReturn(Optional.empty());
    when(userRepository.save(newUser)).thenReturn(newUser);
    when(jedisPool.getResource()).thenReturn(mock(Jedis.class));

    UserEntity savedUser = userService.createUser(newUser);

    assertEquals(newUser, savedUser);
    assert savedUser.getStatus().equals("PENDING");
  }

  @Test
  public void testFindUserById() {
    UUID userId = UUID.randomUUID();

    // Check null is returned when no user is found
    assertNull(userService.getUserById(userId));

    UserEntity user = new UserEntity(userId, "existing@test.com",
            "Existing User");

    // When the user is searched for return the correct user
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    // Get the user and check the id's match
    UserEntity foundUser = userService.getUserById(userId);
    assert foundUser.getId().equals(userId);

  }

  @Test
  public void testSetCache() {
    UUID userId = UUID.randomUUID();
    UserEntity newUser = new UserEntity(userId, "new@test.com", "New User");

    Jedis mockJedis = mock(Jedis.class);

    when(userRepository.findById(userId)).thenReturn(Optional.empty());
    when(userRepository.save(newUser)).thenReturn(newUser);
    when(jedisPool.getResource()).thenReturn(mockJedis);

    userService.createUser(newUser);

    verify(mockJedis, times(1)).set(eq("user:" + userId), anyString(), any(SetParams.class) );
  }

}
