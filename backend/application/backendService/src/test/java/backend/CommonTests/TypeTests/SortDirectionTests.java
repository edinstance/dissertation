package backend.CommonTests.TypeTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import backend.common.types.SortDirection;
import org.junit.jupiter.api.Test;

public class SortDirectionTests {

  private SortDirection sortDirection;

  @Test
  public void testAscSortDirection() {
    sortDirection = SortDirection.ASC;
    assertNotNull(sortDirection);
  }

  @Test
  public void testDescSortDirection() {
    sortDirection = SortDirection.DESC;
    assertNotNull(sortDirection);
  }
}
