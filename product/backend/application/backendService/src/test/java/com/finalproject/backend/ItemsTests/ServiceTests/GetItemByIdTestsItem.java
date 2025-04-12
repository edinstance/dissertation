package com.finalproject.backend.ItemsTests.ServiceTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.params.SetParams;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetItemByIdTestsItem extends SetupItemServiceTests {

  @Test
  public void testGetItemById() {

    when(jedisPool.getResource()).thenReturn(jedis);

    assertNull(itemService.getItemById(itemId));

    when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

    assert itemService.getItemById(itemId).equals(item);
    verify(itemRepository, times(2)).findById(itemId);
  }

  @Test
  public void testFindItemByIdRefreshCacheItem() throws JsonProcessingException {

    when(jedisPool.getResource()).thenReturn(jedis);
    when(jedis.get("item:" + itemId)).thenReturn(objectMapper.writeValueAsString(item));

    itemService.getItemById(itemId);

    verify(jedis, times(1)).expire(anyString(), anyLong());
    verify(jedis, times(0))
            .set(eq("item:" + itemId), anyString(), any(SetParams.class));
    verify(itemRepository, times(0)).findById(itemId);
  }

  @Test
  public void testFindItemByIdWriteCacheItem() {

    when(jedisPool.getResource()).thenReturn(jedis);
    when(jedis.get("item:" + itemId)).thenReturn(null);
    when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

    itemService.getItemById(itemId);

    verify(jedis, times(0)).expire(eq("item:" + itemId), anyLong());
    verify(jedis, times(1))
            .set(eq("item:" + itemId), anyString(), any(SetParams.class));
    verify(itemRepository, times(1)).findById(itemId);
  }
}
