package backend.items.mappers;

import backend.common.helpers.AuthHelpers;
import backend.items.dto.ItemInput;
import backend.items.entities.ItemEntity;
import backend.users.helpers.UserHelpers;
import org.springframework.stereotype.Component;

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
   * The auth helper the mapper will use.
   */
  private final AuthHelpers authHelpers;

  /**
   * A constructor for the mapper.
   *
   * @param inputUserHelpers the helper to interact with.
   */
  public ItemMapper(UserHelpers inputUserHelpers, AuthHelpers inputAuthHelpers) {
    this.userHelpers = inputUserHelpers;
    this.authHelpers = inputAuthHelpers;
  }

  /**
   * Maps ItemInput to ItemEntity.
   *
   * @param itemInput The input to map.
   *
   * @return ItemEntity The item that is returned.
   */
  public ItemEntity mapInputToItem(final ItemInput itemInput) {
    return new ItemEntity(itemInput.getId(), itemInput.getName(),
            itemInput.getDescription(), itemInput.getEndingTime(), itemInput.getPrice(),
            itemInput.getStock(), itemInput.getCategory(),
            itemInput.getImages(), userHelpers.getUserById(authHelpers.getCurrentUserId()));
  }

}