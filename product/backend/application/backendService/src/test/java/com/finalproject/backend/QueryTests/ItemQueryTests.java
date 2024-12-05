package com.finalproject.backend.QueryTests;

import com.finalproject.backend.entities.ItemEntity;
import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.queries.ItemQueries;
import com.finalproject.backend.services.ItemService;
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

  @Test
  public void testSearchForItem(){
    UserEntity userEntity = new UserEntity(UUID.randomUUID(), "seller@test.com",
            "Seller Name");

    UUID id = UUID.randomUUID();
    String endingTime = new Date().toString();
    ItemEntity item = new ItemEntity(id, "name",
            "description", endingTime, new BigDecimal("2.2"),
            1, "category", List.of("image"), userEntity);

    when(itemService.searchForItemsByName("name")).thenReturn(List.of(item));

    List<ItemEntity> result = itemQueries.searchForItems("name");

    assert result.size() == 1;
    assert result.contains(item);
  }
}
