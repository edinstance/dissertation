package backend.ItemsTests.HelperTests;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.items.helpers.ItemCacheHelpers;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@ExtendWith(MockitoExtension.class)
public class ItemHelperTests {

  @Mock
  private JedisPool jedisPool;

  @Mock
  private Jedis jedis;

  @InjectMocks
  private ItemCacheHelpers itemCacheHelpers;

  @Test
  public void testInvalidateUserItemsCache() {
    UUID userId = UUID.randomUUID();
    String pattern = "user:" + userId + ":items:page:*";

    Set<String> mockKeys = new HashSet<>();
    mockKeys.add("user:" + userId + ":items:page:0");
    mockKeys.add("user:" + userId + ":items:page:1");

    when(jedisPool.getResource()).thenReturn(jedis);
    when(jedis.keys(pattern)).thenReturn(mockKeys);

    itemCacheHelpers.invalidateUserItems(userId);

    verify(jedis).keys(pattern);
    verify(jedis).del(mockKeys.toArray(new String[0]));
    verify(jedis).del(mockKeys.toArray(new String[1]));
  }

  @Test
  public void testInvalidateItemsNoKeys() {
    UUID userId = UUID.randomUUID();
    String pattern = "user:" + userId + ":items:page:*";

    Set<String> mockKeys = new HashSet<>();

    when(jedisPool.getResource()).thenReturn(jedis);
    when(jedis.keys(pattern)).thenReturn(mockKeys);

    itemCacheHelpers.invalidateUserItems(userId);

    verify(jedis).keys(pattern);
    verify(jedis, times(0)).del(mockKeys.toArray(new String[0]));
  }
}
