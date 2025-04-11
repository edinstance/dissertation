package com.finalproject.backend.ItemsTests.ServiceTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.finalproject.backend.common.dto.PaginationInput;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.params.SetParams;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetItemsByUserTests extends SetupServiceTests {

  @Test
  public void testGetItemsByUserNoCache() throws JsonProcessingException {
    when(jedisPool.getResource()).thenReturn(jedis);
    when(itemRepository.getUserItems(user.getId(), true, 0, 10)).thenReturn(List.of(item));
    when(itemRepository.getUserItemsPages(user.getId(), true, 10)).thenReturn(2);

    itemService.getItemsByUser(user.getId(), true, new PaginationInput(0, 10));

    verify(itemRepository, times(1)).getUserItems(user.getId(), true, 0, 10);
    verify(itemRepository, times(1)).getUserItemsPages(user.getId(), true, 10);
    verify(jedis, times(0)).expire(anyString(), anyLong());
    verify(jedis, times(1)).set(eq("user:" + user.getId() + ":items:page:" + 0 + "active:true"), anyString(), any(SetParams.class));
  }

  @Test
  public void testGetItemsByUserCache() throws JsonProcessingException {
    when(jedisPool.getResource()).thenReturn(jedis);
    when(jedis.get("user:" + user.getId() + ":items:page:" + 0 + "active:true"))
            .thenReturn(objectMapper.writeValueAsString(List.of(item)));

    itemService.getItemsByUser(user.getId(), true, new PaginationInput(0, 10));

    verify(jedis, times(1)).expire(anyString(), anyLong());
    verify(itemRepository, times(0)).getUserItems(user.getId(), true, 0, 10);
  }
}
