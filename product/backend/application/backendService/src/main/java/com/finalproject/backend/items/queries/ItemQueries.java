package com.finalproject.backend.items.queries;

import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.common.dto.PaginationInput;
import com.finalproject.backend.items.dto.SearchedItemsResponse;
import com.finalproject.backend.items.entities.ItemEntity;
import com.finalproject.backend.items.services.ItemService;
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
   * @return items with similar names to the search text.
   */
  @DgsQuery
  public SearchedItemsResponse searchForItems(
          @InputArgument String searchText,
          @InputArgument PaginationInput pagination) {

    if (pagination == null) {
      pagination = new PaginationInput(0, 10);
    } else {
      if (pagination.getSize() == 0) {
        pagination.setSize(10);
      }
    }

    return itemService.searchForItemsByName(searchText, pagination);

  }

  /**
   * This is a query to get the items of a user.
   *
   * @param id The id of the user to search against.
   * @param isActive If the items are active or not.
   * @param pagination The pagination input.
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
}
