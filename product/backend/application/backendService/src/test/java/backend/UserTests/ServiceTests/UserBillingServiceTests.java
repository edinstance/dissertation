package backend.UserTests.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import backend.common.helpers.AuthHelpers;
import backend.users.dto.UserBillingInput;
import backend.users.entities.UserBillingEntity;
import backend.users.repositories.UserBillingRepository;
import backend.users.services.UserBillingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

@ExtendWith(MockitoExtension.class)
class UserBillingServiceTests {


  @Mock
  private UserBillingRepository userBillingRepository;

  @Mock
  private JedisPool jedisPool;

  @Mock
  private Jedis jedis;

  @Mock
  private AuthHelpers authHelpers;

  @Mock
  private ObjectMapper mockObjectMapper;

  @InjectMocks
  private UserBillingService userBillingService;


  private final ObjectMapper objectMapper = new ObjectMapper();
  private final UUID userId = UUID.randomUUID();
  private final String accountId = "accountId";
  private final String customerId = "customerId";
  private UserBillingInput input;
  private UserBillingEntity entity;

  @BeforeEach
  void setUp() {
    input = new UserBillingInput(accountId, customerId);
    entity = new UserBillingEntity(userId, accountId, customerId);
  }

  @Test
  public void testSaveUserBilling() {
    when(authHelpers.getCurrentUserId()).thenReturn(userId);
    when(jedisPool.getResource()).thenReturn(jedis);
    when(userBillingRepository.saveUserBilling(userId, accountId, customerId)).thenReturn(entity);

    UserBillingEntity result = userBillingService.saveUserBilling(input);

    assertNotNull(result);
    assertEquals(userId, result.getUserId());
    assertEquals(accountId, result.getAccountId());
    assertEquals(customerId, result.getCustomerId());

    verify(jedis).del("user:" + userId + ":billing");
  }

  @Test
  public void testSaveUserBillingError() {
    when(authHelpers.getCurrentUserId()).thenReturn(userId);
    when(jedisPool.getResource()).thenThrow(new RuntimeException());
    when(userBillingRepository.saveUserBilling(userId, accountId, customerId)).thenReturn(entity);

    UserBillingEntity result = userBillingService.saveUserBilling(input);

    assertNotNull(result);
    assertEquals(userId, result.getUserId());
    assertEquals(accountId, result.getAccountId());
    assertEquals(customerId, result.getCustomerId());

    verify(jedis, never()).del("user:" + userId + ":billing");
  }

  @Test
  public void testGetUserBillingFromCache() throws JsonProcessingException {
    when(authHelpers.getCurrentUserId()).thenReturn(userId);
    when(jedisPool.getResource()).thenReturn(jedis);
    when(jedis.get("user:" + userId + ":billing")).thenReturn(objectMapper.writeValueAsString(entity));
    when(mockObjectMapper.convertValue(any(), eq(UserBillingEntity.class)))
            .thenReturn(entity);

    UserBillingEntity result = userBillingService.getUserBilling();

    assertNotNull(result);
    assertEquals(userId, result.getUserId());
    assertEquals(accountId, result.getAccountId());
    assertEquals(customerId, result.getCustomerId());
    verify(jedis).get("user:" + userId + ":billing");
    verify(userBillingRepository, never()).findById(userId);
  }

  @Test
  public void testGetUserBillingFromDatabase() throws JsonProcessingException {
    when(authHelpers.getCurrentUserId()).thenReturn(userId);
    when(jedisPool.getResource()).thenReturn(jedis);
    when(jedis.get("user:" + userId + ":billing")).thenReturn(null);
    when(userBillingRepository.findById(userId)).thenReturn(Optional.of(entity));
    when(mockObjectMapper.writeValueAsString(any())).thenReturn(objectMapper.writeValueAsString(entity));

    UserBillingEntity result = userBillingService.getUserBilling();

    assertNotNull(result);
    assertEquals(userId, result.getUserId());
    assertEquals(accountId, result.getAccountId());
    assertEquals(customerId, result.getCustomerId());
    verify(jedis, times(1)).get("user:" + userId + ":billing");
    verify(userBillingRepository).findById(userId);
    verify(jedis, times(1)).setex("user:" + userId + ":billing", 3600, objectMapper.writeValueAsString(entity));
  }

  @Test
  public void testGetUserBillingFromDatabaseNull() throws JsonProcessingException {
    when(authHelpers.getCurrentUserId()).thenReturn(userId);
    when(jedisPool.getResource()).thenReturn(jedis);
    when(jedis.get("user:" + userId + ":billing")).thenReturn(null);
    when(userBillingRepository.findById(userId)).thenReturn(Optional.empty());

    UserBillingEntity result = userBillingService.getUserBilling();

    assertNull(result);
    verify(jedis, times(1)).get("user:" + userId + ":billing");
    verify(userBillingRepository).findById(userId);
    verify(jedis, times(0)).setex("user:" + userId + ":billing", 3600, objectMapper.writeValueAsString(entity));
  }

  @Test
  public void testGetUserBillingError() throws JsonProcessingException {
    when(authHelpers.getCurrentUserId()).thenReturn(userId);
    when(jedisPool.getResource()).thenThrow(new RuntimeException());

    UserBillingEntity result = userBillingService.getUserBilling();

    assertNull(result);
    verify(jedis, never()).get("user:" + userId + ":billing");
    verify(userBillingRepository, never()).findById(userId);
    verify(jedis, never()).setex("user:" + userId + ":billing", 3600, objectMapper.writeValueAsString(entity));
  }


}
