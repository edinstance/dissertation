package com.finalproject.backend.ItemsTests.ServiceTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.finalproject.backend.items.entities.ItemEntity;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.params.SetParams;
import java.sql.Timestamp;
import java.text.ParseException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SaveOrUpdateItemsTests extends SetupServiceTests {

  @Test
  public void saveOrUpdateItem() throws JsonProcessingException, ParseException {

    when(authHelpers.getCurrentUserId()).thenReturn(user.getId());

    when(itemRepository.saveOrUpdateItem(itemId,
            item.getName(),
            item.getDescription(),
            item.getIsActive(),
            new Timestamp(dateFormat.parse(item.getEndingTime()).getTime()),
            item.getPrice(),
            item.getStock(),
            item.getCategory(),
            objectMapper.writeValueAsString(item.getImages()),
            user.getId())).thenReturn(item);

    when(jedisPool.getResource()).thenReturn(jedis);

    ItemEntity returnedItem = itemService.saveOrUpdateItem(item);
    verify(itemRepository).saveOrUpdateItem(itemId,
            item.getName(),
            item.getDescription(),
            item.getIsActive(),
            new Timestamp(dateFormat.parse(item.getEndingTime()).getTime()),
            item.getPrice(),
            item.getStock(),
            item.getCategory(),
            objectMapper.writeValueAsString(item.getImages()),
            item.getSeller().getId());

    assertEquals(returnedItem.getId(), itemId);
    assertEquals(returnedItem.getName(), item.getName());
    assertEquals(returnedItem.getDescription(), item.getDescription());
    assertEquals(returnedItem.getIsActive(), item.getIsActive());
    assertEquals(returnedItem.getPrice(), item.getPrice());
    assertEquals(returnedItem.getStock(), item.getStock());
    assertEquals(returnedItem.getCategory(), item.getCategory());
    assertEquals(returnedItem.getImages(), item.getImages());
    assertEquals(returnedItem.getSeller().getId(), item.getSeller().getId());

  }

  @Test
  public void testSaveOrUpdateCacheWrite() throws ParseException, JsonProcessingException {

    when(authHelpers.getCurrentUserId()).thenReturn(user.getId());

    when(itemRepository.saveOrUpdateItem(itemId,
            item.getName(),
            item.getDescription(),
            item.getIsActive(),
            new Timestamp(dateFormat.parse(item.getEndingTime()).getTime()),
            item.getPrice(),
            item.getStock(),
            item.getCategory(),
            objectMapper.writeValueAsString(item.getImages()),
            user.getId())).thenReturn(item);

    when(jedisPool.getResource()).thenReturn(jedis);

    itemService.saveOrUpdateItem(item);

    verify(jedis, times(1))
            .set(eq("item:" + itemId), anyString(), any(SetParams.class));
  }

  @Test
  public void testUserItemsCacheInvalidate() throws JsonProcessingException, ParseException {
    when(authHelpers.getCurrentUserId()).thenReturn(user.getId());

    when(itemRepository.saveOrUpdateItem(itemId,
            item.getName(),
            item.getDescription(),
            item.getIsActive(),
            new Timestamp(dateFormat.parse(item.getEndingTime()).getTime()),
            item.getPrice(),
            item.getStock(),
            item.getCategory(),
            objectMapper.writeValueAsString(item.getImages()),
            user.getId())).thenReturn(item);

    when(jedisPool.getResource()).thenReturn(jedis);

    itemService.saveOrUpdateItem(item);

    verify(itemCacheHelpers).invalidateUserItems(user.getId());
  }

}
