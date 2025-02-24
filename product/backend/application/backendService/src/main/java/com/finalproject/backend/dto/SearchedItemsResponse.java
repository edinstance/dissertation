package com.finalproject.backend.dto;

import com.finalproject.backend.entities.ItemEntity;
import com.finalproject.backend.helpers.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
   * Default constructor for this class.
   */
  public SearchedItemsResponse() {

  }
  /**
   * A constructor with the items and pagination.
   *
   * @param items The items for the response.
   * @param pagination The pagination information for the response.
   */
  public SearchedItemsResponse(List<ItemEntity> items, Pagination pagination) {
    this.items = items;
    this.pagination = pagination;
  }
}
