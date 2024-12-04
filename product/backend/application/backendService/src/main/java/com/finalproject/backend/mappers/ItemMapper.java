package com.finalproject.backend.mappers;

import com.finalproject.backend.dto.ItemInput;
import com.finalproject.backend.entities.ItemEntity;
import com.finalproject.backend.helpers.UserHelpers;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Mapper for converting between ItemDTO and Item entity.
 */
@Component
public class ItemMapper {

  /**
   * The user helper the mapper will use.
   */
  private final UserHelpers userHelpers;

  /**
   * A constructor for the mapper.
   *
   * @param inputUserHelpers the helper to interact with.
   */
  public ItemMapper(UserHelpers inputUserHelpers) {
    this.userHelpers = inputUserHelpers;
  }

  /**
   * Maps ItemInput to ItemEntity.
   *
   * @param itemInput The input to map.
   * @return ItemEntity The item that is returned.
   */
  public ItemEntity mapInputToItem(final ItemInput itemInput) {
    return new ItemEntity(itemInput.getId(), itemInput.getName(),
            itemInput.getDescription(), itemInput.getEndingTime(),  itemInput.getPrice(),
            itemInput.getStock(), itemInput.getCategory(),
            itemInput.getImages(),userHelpers.getUserById(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")));
  }

}