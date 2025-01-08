package com.finalproject.backend.MutationTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.finalproject.backend.dto.ItemInput;
import com.finalproject.backend.entities.ItemEntity;
import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.mappers.ItemMapper;
import com.finalproject.backend.mutations.ItemMutations;
import com.finalproject.backend.services.ItemService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ItemMutationTests {

  @Mock
  private ItemService itemService;

  @Mock
  private ItemMapper itemMapper;

  @InjectMocks
  private ItemMutations itemMutations;

  @Test
  public void testSaveItemMutation() throws ParseException, JsonProcessingException {
    UserEntity userEntity = new UserEntity(UUID.randomUUID(), "seller@test.com",
            "Seller Name");

    UUID id = UUID.randomUUID();
    String endingTime = new Date().toString();
    ItemInput input = new ItemInput(id, "name", "description", true,
            endingTime, new BigDecimal("2.2"), 1,
            "category", List.of("image"));
    ItemEntity item = new ItemEntity(id, "name",
            "description", endingTime, new BigDecimal("2.2"),
            1, "category", List.of("image"), userEntity);


    when(itemMapper.mapInputToItem(any(ItemInput.class)))
            .thenReturn(item);

    when(itemService.saveOrUpdateItem(any(ItemEntity.class))).thenReturn(item);

    ItemEntity result = itemMutations.saveItem(input);

    assert result != null;
    assertEquals("name", item.getName());
    assertEquals("description", item.getDescription());
    assertEquals(endingTime, item.getEndingTime());
    assertEquals(true, item.getIsActive());
    assertEquals(new BigDecimal("2.2"), item.getPrice());
    assertEquals(1, item.getStock());
    assertEquals("category", item.getCategory());
    assertEquals(List.of("image"), item.getImages());
    assertEquals(userEntity, item.getSeller());

    verify(itemService).saveOrUpdateItem(any(ItemEntity.class));
  }
}
