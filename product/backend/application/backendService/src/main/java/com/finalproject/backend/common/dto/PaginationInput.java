package com.finalproject.backend.common.dto;

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
  private int size;

  /**
   * Default constructor.
   */
  public PaginationInput() {
  }

  /**
   * This constructor creates a PaginationInput with the specified details.
   *
   * @param inputPage     The Page.
   * @param inputSize The size of the page.
   */
  public PaginationInput(final int inputPage, final int inputSize) {
    this.page = inputPage;
    this.size = inputSize;
  }
}
