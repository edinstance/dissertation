package com.finalproject.backend.items.dto;

import com.finalproject.backend.common.helpers.Pagination;
import com.finalproject.backend.common.helpers.Sorting;
import com.finalproject.backend.items.entities.ItemEntity;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * This is a dto for a list of items and the pagination data.
 */
@Getter
@Setter
public class SearchedItemsResponse {

  /**
   * The list of items.
   */
  private List<ItemEntity> items;

  /**
   * The pagination information.
   */
  private Pagination pagination;

  /**
   * The sorting information.
   */
  private Sorting sorting;

  /**
   * Default constructor for this class.
   */
  public SearchedItemsResponse() {

  }

  /**
   * A constructor with the items and pagination.
   *
   * @param items      The items for the response.
   * @param pagination The pagination information for the response.
   */
  public SearchedItemsResponse(List<ItemEntity> items, Pagination pagination) {
    this.items = items;
    this.pagination = pagination;
  }

  /**
   * A constructor with the items, pagination and sorting.
   *
   * @param items      The items for the response.
   * @param pagination The pagination information for the response.
   * @param sorting    The sorting information for the response.
   */
  public SearchedItemsResponse(List<ItemEntity> items, Pagination pagination,
                               Sorting sorting) {
    this.items = items;
    this.pagination = pagination;
    this.sorting = sorting;
  }
}
