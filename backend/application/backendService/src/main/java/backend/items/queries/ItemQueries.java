package backend.items.queries;

import backend.common.config.logging.AppLogger;
import backend.common.dto.PaginationInput;
import backend.common.dto.SortInput;
import backend.common.types.SortDirection;
import backend.items.dto.SearchedItemsResponse;
import backend.items.entities.ItemEntity;
import backend.items.services.ItemService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.util.UUID;

/**
 * This class contains the user queries.
 */
@DgsComponent
public class ItemQueries {

  /**
   * This is the service the query uses.
   */
  private final ItemService itemService;

  /**
   * The constructor for the queries.
   *
   * @param inputItemService the service the query uses.
   */
  public ItemQueries(ItemService inputItemService) {
    this.itemService = inputItemService;
  }

  /**
   * Query for getting an item from an id.
   *
   * @param id the id of the item.
   *
   * @return the item that was found.
   */
  @DgsQuery
  public ItemEntity getItemById(@InputArgument("id") String id) {
    AppLogger.info("Get item by id: " + id);
    return itemService.getItemById(UUID.fromString(id));
  }

  /**
   * Query for searching for items based on an input text.
   *
   * @param searchText the text to search against.
   *
   * @return items with similar names to the search text.
   */
  @DgsQuery
  public SearchedItemsResponse searchForItems(
          @InputArgument String searchText,
          @InputArgument SortInput sorting,
          @InputArgument PaginationInput pagination) {

    if (pagination == null) {
      pagination = new PaginationInput(0, 10);
    } else {
      if (pagination.getSize() == 0) {
        pagination.setSize(10);
      }
    }

    if (sorting == null) {
      sorting = new SortInput("ending_time", SortDirection.ASC);
    }


    return itemService.searchForItemsByName(searchText, pagination, sorting);

  }

  /**
   * This is a query to get the items of a user.
   *
   * @param id         The id of the user to search against.
   * @param isActive   If the items are active or not.
   * @param pagination The pagination input.
   *
   * @return The items and pagination information.
   */
  @DgsQuery
  public SearchedItemsResponse getItemsByUser(
          @InputArgument String id,
          @InputArgument Boolean isActive,
          @InputArgument PaginationInput pagination) {

    if (pagination == null) {
      pagination = new PaginationInput(0, 10);
    } else {
      if (pagination.getSize() == 0) {
        pagination.setSize(10);
      }
    }

    if (isActive == null) {
      isActive = true;
    }

    return itemService.getItemsByUser(UUID.fromString(id), isActive, pagination);
  }

  /**
   * This query gets the shop items.
   *
   * @param pagination The pagination information.
   * @param sorting    The sorting information.
   *
   * @return The items for the shop.
   */
  @DgsQuery
  public SearchedItemsResponse getShopItems(
          @InputArgument PaginationInput pagination,
          @InputArgument SortInput sorting
  ) {

    if (pagination == null) {
      pagination = new PaginationInput(0, 10);
    } else {
      if (pagination.getSize() == 0) {
        pagination.setSize(10);
      }
    }

    if (sorting == null) {
      sorting = new SortInput("ending_time", SortDirection.ASC);
    }

    return itemService.getShopItems(pagination, sorting);
  }

  /**
   * Query to get users won items.
   *
   * @param pagination pagination input.
   *
   * @return the users won items.
   */
  @DgsQuery
  public SearchedItemsResponse getUsersWonItems(@InputArgument PaginationInput pagination) {

    if (pagination == null) {
      pagination = new PaginationInput(0, 10);
    } else {
      if (pagination.getSize() == 0) {
        pagination.setSize(10);
      }
    }
    return itemService.getUsersWonItems(pagination);
  }
}
