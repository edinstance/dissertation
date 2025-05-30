package backend.UserTests.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.common.helpers.AuthHelpers;
import backend.permissions.authorizers.AdminAuthorizer;
import backend.users.entities.UserEntity;
import backend.users.repositories.UserRepository;
import backend.users.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Mock
  private UserRepository userRepository;

  @Mock
  private JedisPool jedisPool;

  @Mock
  private Jedis jedis;

  @Mock
  private AuthHelpers authHelpers;

  @Mock
  private AdminAuthorizer adminAuthorizer;

  @InjectMocks
  private UserService userService;

  private UserEntity user;
  private UUID userId;

  @BeforeEach
  public void setup() {
    userId = UUID.randomUUID();
    user = new UserEntity(userId, "existing@test.com", "Existing User");

  }

  @Test
  public void testCreateExistingUser() {
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    UserEntity newUser = new UserEntity(userId, "new@test.com", "New User");

    assertThrows(IllegalArgumentException.class, () -> userService.createUser(newUser), "User with UUID " + newUser.getId() + " already exists.");
  }

  @Test
  public void testCreateNewUser() {

    when(userRepository.findById(userId)).thenReturn(Optional.empty());
    when(userRepository.save(user)).thenReturn(user);
    when(jedisPool.getResource()).thenReturn(jedis);

    UserEntity savedUser = userService.createUser(user);

    assertEquals(user, savedUser);
    assert savedUser.getStatus().equals("PENDING");
  }

  @Test
  public void testFindUserById() {

    when(jedisPool.getResource()).thenReturn(jedis);

    // Check null is returned when no user is found
    assertNull(userService.getUserById(userId));

    // When the user is searched for return the correct user
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    // Get the user and check the id's match
    UserEntity foundUser = userService.getUserById(userId);
    assert foundUser.getId().equals(userId);

  }

  @Test
  public void testSetCache() {

    when(userRepository.findById(userId)).thenReturn(Optional.empty());
    when(userRepository.save(user)).thenReturn(user);
    when(jedisPool.getResource()).thenReturn(jedis);

    userService.createUser(user);

    verify(jedis, times(1))
            .set(eq("user:" + userId), anyString(), any(SetParams.class));
  }

  @Test
  public void testGetUserByIdFromCache() throws Exception {

    when(jedisPool.getResource()).thenReturn(jedis);

    String userString = objectMapper.writeValueAsString(user);
    when(jedis.get("user:" + userId)).thenReturn(userString);

    UserEntity result = userService.getUserById(userId);

    assertNotNull(result);
    assertEquals(userId, result.getId());
    assertEquals("existing@test.com", result.getEmail());
    assertEquals("Existing User", result.getName());

    verify(jedis).get("user:" + userId);
    verify(jedis, times(1))
            .expire("user:" + userId, 300);
    verify(userRepository, never()).findById(userId);
  }

  @Test
  public void testCacheRefresh() {

    when(jedisPool.getResource()).thenReturn(jedis);
    when(jedis.get("user:" + userId)).thenReturn(null);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    UserEntity foundUser = userService.getUserById(userId);

    assertNotNull(foundUser);
    assertEquals(userId, foundUser.getId());

    verify(jedis, times(1)).set(eq("user:" + userId), anyString(), eq(SetParams.setParams().ex(300)));
  }

  @Test
  public void testDeletedUserIsNotReturned() {
    user.setIsDeleted(true);

    when(jedisPool.getResource()).thenReturn(jedis);
    when(jedis.get("user:" + userId)).thenReturn(null);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    UserEntity foundUser = userService.getUserById(userId);

    assertNull(foundUser);

  }

  @Test
  public void testDeleteUserById() {
    when(jedisPool.getResource()).thenReturn(jedis);
    when(authHelpers.getCurrentUserId()).thenReturn(userId);

    assert (userService.deleteUser());

    verify(jedis).del(eq("user:" + userId));
    verify(userRepository).deleteUser(userId);
  }

  @Test
  public void testDeleteUserException() {
    when(jedisPool.getResource()).thenReturn(jedis);
    when(authHelpers.getCurrentUserId()).thenReturn(userId);

    doThrow(new RuntimeException()).when(userRepository).deleteUser(userId);
    assertFalse(userService.deleteUser());
  }

  @Test
  public void testDeactivateUser() {
    userService.deactivateUser(userId);

    verify(userRepository).deactivateUser(userId);
  }

}
