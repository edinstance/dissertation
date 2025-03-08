package com.finalproject.backend.common.helpers;

import com.finalproject.backend.common.types.SortDirection;
import lombok.Getter;
import lombok.Setter;

/**
 * This class contains sorting information.
 */
@Getter
@Setter
public class Sorting {

  /**
   * What to sort by.
   */
  private String sortBy;

  /**
   * What direction to sort in.
   */
  private SortDirection sortDirection;

  /**
   * Default constructor.
   */
  public Sorting(){

  }

  /**
   * Constructor with options.
   *
   * @param sortBy What to sort by.
   * @param sortDirection The direction to sort in.
   */
  public Sorting(String sortBy, SortDirection sortDirection) {
    this.sortBy = sortBy;
    this.sortDirection = sortDirection;
  }
}
