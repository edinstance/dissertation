package backend.CommonTests.HelperTests;

import backend.common.helpers.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PaginationTests {

  private Pagination pagination;

  @Test
  public void testDefaultConstructor() {
    pagination = new Pagination();

    assertNotNull(pagination);
  }

  @Test
  public void testPaginationPageSizeConstructor() {
    pagination = new Pagination(1, 1);

    assertNotNull(pagination);
    assert pagination.getSize() == 1;
    assert pagination.getPage() == 1;
  }

  @Test
  public void testPaginationConstructor() {
    pagination = new Pagination(1, 1, 1);

    assertNotNull(pagination);
    assert pagination.getSize() == 1;
    assert pagination.getPage() == 1;
    assert pagination.getTotal() == 1;
  }

  @BeforeEach
  void setUp() {
    pagination = new Pagination(1, 1, 1);
  }

  @Test
  public void testPageMethods() {
    pagination.setPage(2);

    assert pagination.getPage() == 2;
  }

  @Test
  public void testTotalMethods() {
    pagination.setTotal(3);

    assert pagination.getTotal() == 3;
  }

  @Test
  public void testSizeMethods() {
    pagination.setSize(2);

    assert pagination.getSize() == 2;
  }
}
