package com.finalproject.backend.QueryTests;

import com.finalproject.backend.entities.ItemEntity;
import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.queries.ItemQueries;
import com.finalproject.backend.services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemQueryTests {

  @Mock
  ItemService itemService;

  @InjectMocks
  ItemQueries itemQueries;
  private ItemEntity item;

  @BeforeEach
  void setUp() {
    UserEntity user = new UserEntity(UUID.randomUUID(), "seller@test.com",
            "Seller Name");

    UUID id = UUID.randomUUID();
    String endingTime = new Date().toString();
    item = new ItemEntity(id, "name",
            "description", endingTime, new BigDecimal("2.2"),
            1, "category", List.of("image"), user);
  }

  @Test
  public void testSearchForItem() {
    when(itemService.searchForItemsByName("name")).thenReturn(List.of(item));

    List<ItemEntity> result = itemQueries.searchForItems("name");

    assert result.size() == 1;
    assert result.contains(item);
  }

  @Test
  public void testGetItemById() {

    when(itemService.getItemById(item.getId())).thenReturn(item);

    ItemEntity result = itemQueries.getItemById(item.getId().toString());

    assert result.getId().equals(item.getId());
  }
}
