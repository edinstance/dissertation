package backend.common.helpers;

import lombok.Getter;
import lombok.Setter;

/**
 * This class contains the pagination logic.
 */
@Getter
@Setter
public class Pagination {

  /**
   * The page index.
   */
  private int page;

  /**
   * The size of each page.
   */
  private int size;

  /**
   * The total number of pages.
   */
  private int total;

  /**
   * Default constructor.
   */
  public Pagination() {

  }

  /**
   * Constructor with just page and size.
   *
   * @param page The page.
   * @param size The size of the page.
   */
  public Pagination(int page, int size) {
    this.page = page;
    this.size = size;
  }

  /**
   * Constructor with all variables.
   *
   * @param page The page.
   * @param size The size of the page.
   * @param total The total amount of pages.
   */
  public Pagination(int page, int size, int total) {
    this.page = page;
    this.size = size;
    this.total = total;
  }
}
