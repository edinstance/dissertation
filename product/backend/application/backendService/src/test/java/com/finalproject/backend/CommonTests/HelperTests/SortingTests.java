package com.finalproject.backend.CommonTests.HelperTests;


import com.finalproject.backend.common.helpers.Sorting;
import com.finalproject.backend.common.types.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SortingTests {

  private Sorting sorting;

  @Test
  public void testDefaultConstructor() {
    sorting = new Sorting();

    assertNotNull(sorting);
  }

  @Test
  public void testSortingConstructor() {
    sorting = new Sorting("date", SortDirection.ASC);

    assertNotNull(sorting);
    assert sorting.getSortDirection() == SortDirection.ASC;
    assert sorting.getSortBy().equals("date");
  }

  @BeforeEach
  void setUp() {
    sorting = new Sorting("date", SortDirection.ASC);
  }

  @Test
  public void testSortByMethods() {
    sorting.setSortBy("new");
    assert sorting.getSortBy().equals("new");
  }

  @Test
  public void testSortDirectionMethods() {
    sorting.setSortDirection(SortDirection.DESC);
    assert sorting.getSortDirection() == SortDirection.DESC;
  }
}
