package backend.CommonTests.dtoTests;

import backend.common.dto.PaginationInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PaginationInputTests {

  private PaginationInput paginationInput;

  @Test
  public void testDefaultConstructor() {
    paginationInput = new PaginationInput();

    assertNotNull(paginationInput);
  }

  @Test
  public void testPaginationInputConstructor() {
    paginationInput = new PaginationInput(1, 1);

    assertNotNull(paginationInput);
    assert paginationInput.getPage() == 1;
    assert paginationInput.getSize() == 1;
  }

  @BeforeEach
  void setUp() {
    paginationInput = new PaginationInput(1, 1);
  }

  @Test
  public void testPageMethods() {
    paginationInput.setPage(2);
    assert paginationInput.getPage() == 2;
  }

  @Test
  public void testPageSizeMethods() {
    paginationInput.setSize(2);
    assert paginationInput.getSize() == 2;
  }

}
