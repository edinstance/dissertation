package com.finalproject.backend.items.types;

import com.finalproject.backend.common.config.logging.AppLogger;

/**
 * This enum contains what the items can be sorted by.
 */
public enum ItemSortOptions {
  PRICE("price"),
  ENDING_TIME("ending_time"),
  STOCK("stock");


  private final String value;

  /**
   * This constructor sets the sort options value.
   *
   * @param value The value to be set.
   */
  ItemSortOptions(String value) {
    this.value = value;
  }

  /**
   * This function gets the string value of an ItemSortOption.
   *
   * @return The string value of the option.
   */
  public String getValue() {
    return value;
  }

  /**
   *This method is used to get the ItemSortOptions value from a string.
   *
   * @param value the string that is being passed in.
   * @return the ItemSortOptions value of the string.
   */
  public static ItemSortOptions stringToSortOption(String value) {
    for (ItemSortOptions sortOption : ItemSortOptions.values()) {
      if (sortOption.value.equals(value)) {
        return sortOption;
      }
    }
    AppLogger.error("Unsupported sort option: " + value);
    throw new IllegalArgumentException("Unknown sort option value: " + value);
  }

}
