package com.finalproject.backend.queries;

import com.finalproject.backend.entities.ItemEntity;
import com.finalproject.backend.services.ItemService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;

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
   * Query for searching for items based on an input text.
   *
   * @param searchText the text to search against.
   * @return items with similar names to the search text.
   */
  @DgsQuery
  public List<ItemEntity> searchForItems(
          @InputArgument String searchText){

    return itemService.searchForItemsByName(searchText);

  }
}
