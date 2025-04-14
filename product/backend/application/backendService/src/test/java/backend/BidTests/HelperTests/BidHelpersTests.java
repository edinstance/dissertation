package backend.BidTests.HelperTests;

import backend.bids.dynamodb.BidsDynamoService;
import backend.bids.helpers.BidHelpers;
import backend.common.dynamodb.tables.Bids;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.math.BigDecimal;
import java.util.UUID;

import static backend.bids.helpers.BidCacheHelpers.ITEM_CURRENT_BID_CACHE_EXPIRY_SECONDS;
import static backend.bids.helpers.BidCacheHelpers.ITEM_CURRENT_BID_KEY_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidHelpersTests {

  @Mock
  private JedisPool jedisPool;

  @Mock
  private Jedis jedis;

  @Mock
  private BidsDynamoService bidsDynamoService;

  @Mock
  private Bids mockBid;

  private BidHelpers bidHelpers;
  private UUID itemId;
  private String cacheKey;

  @BeforeEach
  void setUp() {
    bidHelpers = new BidHelpers(jedisPool, bidsDynamoService);
    itemId = UUID.randomUUID();
    cacheKey = String.format(ITEM_CURRENT_BID_KEY_FORMAT, itemId);

    // Setup jedisPool to return our mock jedis
    when(jedisPool.getResource()).thenReturn(jedis);
  }

  @Test
  void getCurrentHighestBid_CacheHit_ReturnsCachedValue() {
    // Arrange
    BigDecimal expectedPrice = new BigDecimal("150.00");
    when(jedis.get(cacheKey)).thenReturn(expectedPrice.toString());

    // Act
    BigDecimal result = bidHelpers.getCurrentHighestBid(itemId);

    // Assert
    assertEquals(expectedPrice, result);
    verify(jedis).get(cacheKey);
    verify(bidsDynamoService, never()).getMostRecentBid(any(UUID.class));
  }

  @Test
  void getCurrentHighestBid_CacheMiss_FetchesFromDynamoAndUpdatesCache() {
    // Arrange
    BigDecimal expectedPrice = new BigDecimal("200.00");
    when(jedis.get(cacheKey)).thenReturn(null);
    when(bidsDynamoService.getMostRecentBid(itemId)).thenReturn(mockBid);
    when(mockBid.getAmount()).thenReturn(expectedPrice);

    // Act
    BigDecimal result = bidHelpers.getCurrentHighestBid(itemId);

    // Assert
    assertEquals(expectedPrice, result);
    verify(jedis).get(cacheKey);
    verify(bidsDynamoService).getMostRecentBid(itemId);
    verify(jedis).setex(cacheKey, ITEM_CURRENT_BID_CACHE_EXPIRY_SECONDS, expectedPrice.toString());
  }

  @Test
  void getCurrentHighestBid_JedisException_ThrowsRuntimeException() {
    // Arrange
    when(jedis.get(cacheKey)).thenThrow(new RuntimeException("Redis connection error"));

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () ->
            bidHelpers.getCurrentHighestBid(itemId)
    );
    assertEquals("Failed to retrieve current bid price", exception.getMessage());
    verify(jedis).get(cacheKey);
  }

  @Test
  void getCurrentHighestBid_DynamoException_ThrowsRuntimeException() {
    // Arrange
    when(jedis.get(cacheKey)).thenReturn(null);
    when(bidsDynamoService.getMostRecentBid(itemId))
            .thenThrow(new RuntimeException("DynamoDB error"));

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () ->
            bidHelpers.getCurrentHighestBid(itemId)
    );
    assertEquals("Failed to retrieve current bid price", exception.getMessage());
    verify(jedis).get(cacheKey);
    verify(bidsDynamoService).getMostRecentBid(itemId);
  }

  @Test
  void getCurrentHighestBid_JedisPoolReturnsNull_ThrowsRuntimeException() {
    // Arrange
    when(jedisPool.getResource()).thenReturn(null);

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () ->
            bidHelpers.getCurrentHighestBid(itemId)
    );
    assertEquals("Failed to retrieve current bid price", exception.getMessage());
  }

  @Test
  void getCurrentHighestBid_VerifyJedisResourceIsClosed() {
    // Arrange
    BigDecimal expectedPrice = new BigDecimal("150.00");
    when(jedis.get(cacheKey)).thenReturn(expectedPrice.toString());

    // Act
    bidHelpers.getCurrentHighestBid(itemId);

    // Assert
    verify(jedis).close();
  }
}