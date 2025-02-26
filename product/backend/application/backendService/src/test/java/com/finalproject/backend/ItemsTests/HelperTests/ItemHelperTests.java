package com.finalproject.backend.ItemsTests.HelperTests;

import com.finalproject.backend.items.helpers.ItemCacheHelpers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    when(jedisPool.getResource()).thenReturn(jedis);

    UUID userId = UUID.randomUUID();

    itemCacheHelpers.invalidateUserItems(userId, 0);

    verify(jedis).del("user:" + userId +":items:page:" + 0);

  }
}
