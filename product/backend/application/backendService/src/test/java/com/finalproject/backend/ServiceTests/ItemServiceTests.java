package com.finalproject.backend.ServiceTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.entities.ItemEntity;
import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.repositories.ItemRepository;
import com.finalproject.backend.services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
  @Mock
  private ItemRepository itemRepository;
  @InjectMocks
  private ItemService itemService;
  private UUID itemId;
  private ItemEntity item;

  @BeforeEach
  public void setUp() throws ParseException {
    itemId = UUID.randomUUID();
    UserEntity userEntity = new UserEntity(UUID.randomUUID(),
            "seller@test.com", "Seller Name");
    item = new ItemEntity(UUID.randomUUID(), "Item Name",
            "Item Description", dateFormat.format(new Date()), new BigDecimal("19.99"), 100,
            "Category",
            List.of("image"), userEntity);
  }

  @Test
  public void testGetItemById() {

    assertNull(itemService.getItemById(itemId));
    when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

    assert itemService.getItemById(itemId).equals(item);
    verify(itemRepository, times(2)).findById(itemId);
  }

  @Test
  public void testSearchForItems() {
    when(itemRepository.searchForItems("Item Name")).thenReturn(List.of(item));

    assert itemService.searchForItemsByName("Item Name").contains(item);
    verify(itemRepository).searchForItems("Item Name");
  }

  @Test
  public void saveOrUpdateItem() throws JsonProcessingException, ParseException {

    final ObjectMapper objectMapper = new ObjectMapper();

    when(itemRepository.saveOrUpdateItem(item.getId(),
            item.getName(),
            item.getDescription(),
            item.getIsActive(),
            new Timestamp(dateFormat.parse(item.getEndingTime()).getTime()),
            item.getPrice(),
            item.getStock(),
            item.getCategory(),
            objectMapper.writeValueAsString(item.getImages()),
            item.getSeller().getId())).thenReturn(item);

    ItemEntity returnedItem = itemService.saveOrUpdateItem(item);
    verify(itemRepository).saveOrUpdateItem(item.getId(),
            item.getName(),
            item.getDescription(),
            item.getIsActive(),
            new Timestamp(dateFormat.parse(item.getEndingTime()).getTime()),
            item.getPrice(),
            item.getStock(),
            item.getCategory(),
            objectMapper.writeValueAsString(item.getImages()),
            item.getSeller().getId());

    assertEquals(returnedItem.getId(), item.getId());
    assertEquals(returnedItem.getName(), item.getName());
    assertEquals(returnedItem.getDescription(), item.getDescription());
    assertEquals(returnedItem.getIsActive(), item.getIsActive());
    assertEquals(returnedItem.getPrice(), item.getPrice());
    assertEquals(returnedItem.getStock(), item.getStock());
    assertEquals(returnedItem.getCategory(), item.getCategory());
    assertEquals(returnedItem.getImages(), item.getImages());
    assertEquals(returnedItem.getSeller().getId(), item.getSeller().getId());


  }
}
