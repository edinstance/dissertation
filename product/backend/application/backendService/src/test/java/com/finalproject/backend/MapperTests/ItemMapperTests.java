package com.finalproject.backend.MapperTests;

import com.finalproject.backend.dto.ItemInput;
import com.finalproject.backend.entities.ItemEntity;
import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.helpers.UserHelpers;
import com.finalproject.backend.mappers.ItemMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemMapperTests {

  @Mock
  private UserHelpers userHelpers;

  @InjectMocks
  private ItemMapper itemMapper;

  @Test
  public void testInputToEntity() {
    UserEntity userEntity = new UserEntity(UUID.randomUUID(), "seller@test.com",
            "Seller Name");

    UUID id = UUID.randomUUID();
    String endingTime = new Date().toString();
    ItemInput input = new ItemInput(id, "name", "description", true,
            endingTime, new BigDecimal("2.2"), 1,
            "category", List.of("image"));

    when(userHelpers.getCurrentUserId()).thenReturn(userEntity.getId());
    when(userHelpers.getUserById(userEntity.getId())).thenReturn(userEntity);
    ItemEntity item = itemMapper.mapInputToItem(input);

    assertEquals(id, item.getId());
    assertEquals("name", item.getName());
    assertEquals("description", item.getDescription());
    assertEquals(endingTime, item.getEndingTime());
    assertEquals(true, item.getIsActive());
    assertEquals(new BigDecimal("2.2"), item.getPrice());
    assertEquals(1, item.getStock());
    assertEquals("category", item.getCategory());
    assertEquals(List.of("image"), item.getImages());
    assertEquals(userEntity, item.getSeller());

  }
}
