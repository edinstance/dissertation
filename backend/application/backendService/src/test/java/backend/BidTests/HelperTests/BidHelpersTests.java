package backend.BidTests.HelperTests;

import static backend.bids.helpers.BidCacheHelpers.ITEM_CURRENT_BID_CACHE_EXPIRY_SECONDS;
import static backend.bids.helpers.BidCacheHelpers.ITEM_CURRENT_BID_KEY_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.bids.dynamodb.BidsDynamoService;
import backend.bids.helpers.BidHelpers;
import backend.common.dynamodb.tables.Bids;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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

    when(jedisPool.getResource()).thenReturn(jedis);
  }

  @Test
  void testGetHighestBidFromCache() {
    BigDecimal expectedPrice = new BigDecimal("150.00");
    when(jedis.get(cacheKey)).thenReturn(expectedPrice.toString());

    BigDecimal result = bidHelpers.getCurrentHighestBid(itemId);

    assertEquals(expectedPrice, result);
    verify(jedis).get(cacheKey);
    verify(bidsDynamoService, never()).getMostRecentBid(any(UUID.class));
    verify(jedis).expire(cacheKey, ITEM_CURRENT_BID_CACHE_EXPIRY_SECONDS);
  }

  @Test
  void testGetNullBid() {
    when(jedis.get(cacheKey)).thenReturn(null);
    when(bidsDynamoService.getMostRecentBid(itemId)).thenReturn(null);

    BigDecimal result = bidHelpers.getCurrentHighestBid(itemId);

    assertEquals(BigDecimal.ZERO, result);

    verify(jedis).setex(cacheKey, ITEM_CURRENT_BID_CACHE_EXPIRY_SECONDS, BigDecimal.ZERO.toString());
  }

  @Test
  void testGetHighestBidFromDynamo() {
    BigDecimal expectedPrice = new BigDecimal("200.00");
    when(jedis.get(cacheKey)).thenReturn(null);
    when(bidsDynamoService.getMostRecentBid(itemId)).thenReturn(mockBid);
    when(mockBid.getAmount()).thenReturn(expectedPrice);

    BigDecimal result = bidHelpers.getCurrentHighestBid(itemId);

    assertEquals(expectedPrice, result);
    verify(jedis).get(cacheKey);
    verify(bidsDynamoService).getMostRecentBid(itemId);
    verify(jedis).setex(cacheKey, ITEM_CURRENT_BID_CACHE_EXPIRY_SECONDS, expectedPrice.toString());
  }

  @Test
  void testGetNegativeBid() {
    when(jedis.get(cacheKey)).thenReturn(null);
    when(bidsDynamoService.getMostRecentBid(itemId)).thenReturn(mockBid);
    when(mockBid.getAmount()).thenReturn(new BigDecimal("-10.00"));

    BigDecimal result = bidHelpers.getCurrentHighestBid(itemId);

    assertEquals(BigDecimal.ZERO, result);
    verify(jedis).get(cacheKey);
    verify(bidsDynamoService).getMostRecentBid(itemId);
  }


  @Test
  void testJedisReadError() {
    when(jedis.get(cacheKey)).thenThrow(new RuntimeException("Redis connection error"));

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
            bidHelpers.getCurrentHighestBid(itemId)
    );
    assertEquals("Failed to retrieve current bid price", exception.getMessage());
    verify(jedis).get(cacheKey);
  }

  @Test
  void testDynamoError() {
    when(jedis.get(cacheKey)).thenReturn(null);
    when(bidsDynamoService.getMostRecentBid(itemId))
            .thenThrow(new RuntimeException("DynamoDB error"));

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
            bidHelpers.getCurrentHighestBid(itemId)
    );
    assertEquals("Failed to retrieve current bid price", exception.getMessage());
    verify(jedis).get(cacheKey);
    verify(bidsDynamoService).getMostRecentBid(itemId);
  }

  @Test
  void testJedisPoolError() {
    when(jedisPool.getResource()).thenReturn(null);

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
            bidHelpers.getCurrentHighestBid(itemId)
    );
    assertEquals("Failed to retrieve current bid price", exception.getMessage());
  }

}