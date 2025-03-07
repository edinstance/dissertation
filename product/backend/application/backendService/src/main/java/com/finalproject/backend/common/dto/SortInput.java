package com.finalproject.backend.common.dto;

import com.finalproject.backend.common.types.SortDirection;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for sort input.
 */
@Getter
@Setter
public class SortInput {

  /**
   * What to sort by.
   */
  private String sortBy;

  /**
   * The direction of the sorting.
   */
  private SortDirection sortDirection;

  /**
   * Default constructor.
   */
  public SortInput() {
  }

  /**
   * This constructor creates a SortInput with the specified details.
   *
   * @param sortBy What to sort by.
   * @param sortDirection The direction of the sorting.
   */
  public SortInput(final String sortBy, final SortDirection sortDirection) {
    this.sortBy = sortBy;
    this.sortDirection = sortDirection;
  }
}
