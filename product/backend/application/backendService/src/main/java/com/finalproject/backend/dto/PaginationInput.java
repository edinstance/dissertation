package com.finalproject.backend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for pagination input.
 */
@Getter
@Setter
public class PaginationInput {

  /**
   * The page index.
   */
  private int page;

  /**
   * The size of the page.
   */
  private int pageSize;

  /**
   * Default constructor.
   */
  public PaginationInput() {
  }

  /**
   * This constructor creates a PaginationInput with the specified details.
   *
   * @param inputPage     The Page.
   * @param inputPageSize The size of the page.
   */
  public PaginationInput(final int inputPage, final int inputPageSize) {
    this.page = inputPage;
    this.pageSize = inputPageSize;
  }
}
