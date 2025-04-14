package backend.BidTests.HelperTests;


import backend.bids.helpers.BidCacheHelpers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.math.BigDecimal;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BidCacheHelpersTests {

  @Mock
  private JedisPool jedisPool;

  @Mock
  private Jedis jedis;

  @InjectMocks
  private BidCacheHelpers bidCacheHelpers;

  @Test
  void updateCachedHighestBidSuccess() {
    UUID itemId = UUID.randomUUID();
    BigDecimal newAmount = new BigDecimal("100.00");
    String cacheKey = String.format(BidCacheHelpers.ITEM_CURRENT_BID_KEY_FORMAT, itemId);

    when(jedisPool.getResource()).thenReturn(jedis);

    bidCacheHelpers.updateCachedHighestBid(itemId, newAmount);

    verify(jedisPool, times(1)).getResource();
    verify(jedis, times(1))
            .setex(
                    cacheKey,
                    BidCacheHelpers.ITEM_CURRENT_BID_CACHE_EXPIRY_SECONDS,
                    newAmount.toString());
    verify(jedis, times(1)).close();
  }

  @Test
  void updateCachedHighestBidFailure() {
    UUID itemId = UUID.randomUUID();
    BigDecimal newAmount = new BigDecimal("100.00");

    when(jedisPool.getResource()).thenThrow(new RuntimeException("Redis error"));

    bidCacheHelpers.updateCachedHighestBid(itemId, newAmount);

    verify(jedisPool, times(1)).getResource();
    verify(jedis, never())
            .setex(anyString(), anyInt(), anyString());
  }
}