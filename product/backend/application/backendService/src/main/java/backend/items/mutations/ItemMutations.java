package backend.items.mutations;

import com.fasterxml.jackson.core.JsonProcessingException;
import backend.common.config.logging.AppLogger;
import backend.items.dto.ItemInput;
import backend.items.entities.ItemEntity;
import backend.items.mappers.ItemMapper;
import backend.items.services.ItemService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import java.text.ParseException;

/**
 * GraphQL mutations for item-related operations.
 */
@DgsComponent
public class ItemMutations {

  /**
   * The service the mutation will interact with.
   */
  private final ItemService itemService;

  /**
   * The mapper the mutation will use.
   */
  private final ItemMapper itemMapper;

  /**
   * Constructor for initialising the UserMutations with the input services.
   *
   * @param inputItemService The item service to be used by this component.
   * @param inputItemMapper The itemMapper to be used.
   */
  public ItemMutations(final ItemService inputItemService,
                       final ItemMapper inputItemMapper) {
    this.itemService = inputItemService;
    this.itemMapper = inputItemMapper;
  }

  /**
   * GraphQL mutation to create or update an item.
   *
   * @param itemInput The input data for the item.
   */
  @DgsMutation
  public ItemEntity saveItem(
          @InputArgument ItemInput itemInput) throws JsonProcessingException, ParseException {
    ItemEntity item = itemMapper.mapInputToItem(itemInput);
    AppLogger.info("Item saved: " + item);
    return itemService.saveOrUpdateItem(item);
  }
}