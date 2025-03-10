package com.finalproject.backend.ItemsTests.TypesTests;

import static org.junit.jupiter.api.Assertions.*;

import com.finalproject.backend.items.types.ItemSortOptions;
import org.junit.jupiter.api.Test;

class ItemSortOptionsTests {

  @Test
  void testEnumValues() {
    assertEquals(3, ItemSortOptions.values().length, "Should have exactly 3 enum values");
    assertNotNull(ItemSortOptions.PRICE);
    assertNotNull(ItemSortOptions.ENDING_TIME);
    assertNotNull(ItemSortOptions.STOCK);
  }

  @Test
  void testGetValue() {
    assertEquals("price", ItemSortOptions.PRICE.getValue());
    assertEquals("ending_time", ItemSortOptions.ENDING_TIME.getValue());
    assertEquals("stock", ItemSortOptions.STOCK.getValue());
  }

  @Test
  void testStringToSortOption_ValidValues() {
    assertEquals(ItemSortOptions.PRICE, ItemSortOptions.stringToSortOption("price"));
    assertEquals(ItemSortOptions.ENDING_TIME, ItemSortOptions.stringToSortOption("ending_time"));
    assertEquals(ItemSortOptions.STOCK, ItemSortOptions.stringToSortOption("stock"));
  }

  @Test
  void testStringToSortOption_CaseMatters() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ItemSortOptions.stringToSortOption("PRICE");
    });
    assertTrue(exception.getMessage().contains("Unknown sort option value: PRICE"));
  }

  @Test
  void testStringToSortOption_InvalidValue_Empty() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ItemSortOptions.stringToSortOption("");
    });
    assertTrue(exception.getMessage().contains("Unknown sort option value: "));
  }

  @Test
  void testStringToSortOption_InvalidValue_Invalid() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ItemSortOptions.stringToSortOption("invalid");
    });
    assertTrue(exception.getMessage().contains("Unknown sort option value: invalid"));
  }

  @Test
  void testStringToSortOption_InvalidValue_Prices() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ItemSortOptions.stringToSortOption("prices");
    });
    assertTrue(exception.getMessage().contains("Unknown sort option value: prices"));
  }

  @Test
  void testStringToSortOption_InvalidValue_EndingTime() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ItemSortOptions.stringToSortOption("endingtime");
    });
    assertTrue(exception.getMessage().contains("Unknown sort option value: endingtime"));
  }

  @Test
  void testStringToSortOption_NullValue() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ItemSortOptions.stringToSortOption(null);
    });
    assertTrue(exception.getMessage().contains("Unknown sort option value: null"));
  }
}
