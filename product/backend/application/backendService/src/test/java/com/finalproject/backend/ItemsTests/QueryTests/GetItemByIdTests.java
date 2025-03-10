package com.finalproject.backend.ItemsTests.QueryTests;

import com.finalproject.backend.items.entities.ItemEntity;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

public class GetItemByIdTests extends SetupQueryTests {

  @Test
  public void testGetItemById() {

    when(itemService.getItemById(item.getId())).thenReturn(item);

    ItemEntity result = itemQueries.getItemById(item.getId().toString());

    assert result.getId().equals(item.getId());
  }
}
