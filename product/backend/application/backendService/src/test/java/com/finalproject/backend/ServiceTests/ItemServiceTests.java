package com.finalproject.backend.ServiceTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.dto.PaginationInput;
import com.finalproject.backend.entities.ItemEntity;
import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.helpers.AuthHelpers;
import com.finalproject.backend.repositories.ItemRepository;
import com.finalproject.backend.services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTests {

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public ObjectMapper objectMapper = new ObjectMapper();

  @Mock
  private ItemRepository itemRepository;

  @Mock
  private JedisPool jedisPool;

  @Mock
  private Jedis jedis;

  @Mock
  private AuthHelpers authHelpers;

  @InjectMocks
  private ItemService itemService;

  private UUID itemId;
  private UserEntity user;
  private ItemEntity item;
  private List<ItemEntity> items;
  private PaginationInput paginationInput;

  @BeforeEach
  public void setUp() throws ParseException {
    itemId = UUID.randomUUID();
    user = new UserEntity(UUID.randomUUID(),
            "seller@test.com", "Seller Name");
    item = new ItemEntity(itemId, "Item Name",
            "Item Description", dateFormat.format(new Date()), new BigDecimal("19.99"), 100,
            "Category",
            List.of("image"), user);
    paginationInput = new PaginationInput();
  }

  @Test
  public void testGetItemById() {

    when(jedisPool.getResource()).thenReturn(jedis);

    assertNull(itemService.getItemById(itemId));

    when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

    assert itemService.getItemById(itemId).equals(item);
    verify(itemRepository, times(2)).findById(itemId);
  }

  @Test
  public void testSearchForItems() {
    when(itemRepository.searchForItems("Item Name", paginationInput.getPage(), paginationInput.getSize())).thenReturn(List.of(item));

    assert itemService.searchForItemsByName("Item Name", paginationInput).contains(item);
    verify(itemRepository).searchForItems("Item Name", 0, 0);
  }

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
            user.getId())).thenReturn(item);;

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
  public void testFindItemByIdRefreshCacheItem() throws JsonProcessingException, ParseException {

    when(jedisPool.getResource()).thenReturn(jedis);
    when(jedis.get("item:" + itemId)).thenReturn(objectMapper.writeValueAsString(item));

    itemService.getItemById(itemId);

    verify(jedis, times(1)).expire(anyString(), anyLong());
    verify(jedis, times(0))
            .set(eq("item:" + itemId), anyString(), any(SetParams.class));
    verify(itemRepository, times(0)).findById(itemId);
  }

  @Test
  public void testFindItemByIdWriteCacheItem() throws JsonProcessingException, ParseException {

    when(jedisPool.getResource()).thenReturn(jedis);
    when(jedis.get("item:" + itemId)).thenReturn(null);
    when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

    itemService.getItemById(itemId);

    verify(jedis, times(0)).expire(eq("item:" + itemId), anyLong());
    verify(jedis, times(1))
            .set(eq("item:" + itemId), anyString(), any(SetParams.class));
    verify(itemRepository, times(1)).findById(itemId);
  }

  @Test
  public void testCustomSearchPagination(){
    when(itemRepository.searchForItems("Item Name", 2, 3)).thenReturn(List.of(item));

    items = itemService.searchForItemsByName("Item Name", new PaginationInput(2, 3));

    assertEquals(items.getFirst(), item);
    verify(itemRepository, times(1)).searchForItems("Item Name", 2, 3);
  }
}
