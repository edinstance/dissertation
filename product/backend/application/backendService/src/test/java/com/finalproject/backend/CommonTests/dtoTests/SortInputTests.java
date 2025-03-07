package com.finalproject.backend.CommonTests.dtoTests;

import com.finalproject.backend.common.dto.SortInput;
import com.finalproject.backend.common.types.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SortInputTests {

  private SortInput sortInput;

  @Test
  public void testDefaultConstructor() {
    sortInput = new SortInput();

    assertNotNull(sortInput);
  }

  @Test
  public void testPaginationInputConstructor() {
    sortInput = new SortInput("date", SortDirection.ASC);

    assertNotNull(sortInput);
    assert sortInput.getSortBy().equals("date");
    assert sortInput.getSortDirection() == SortDirection.ASC;
  }

  @BeforeEach
  void setUp() {
    sortInput = new SortInput("date", SortDirection.ASC );
  }

  @Test
  public void testSortByMethods() {
    sortInput.setSortBy("new");
    assert sortInput.getSortBy().equals("new");
  }

  @Test
  public void testSortDirectionMethods() {
    sortInput.setSortDirection(SortDirection.DESC);
    assert sortInput.getSortDirection() == SortDirection.DESC;
  }

}
