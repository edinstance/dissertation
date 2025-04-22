package backend.ItemsTests.dtoTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import backend.items.dto.ItemInput;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemInputTests {

  private ItemInput itemInput;
  private UUID id;
  private String endingTime;

  @Test
  public void testDefaultConstructor() {
    itemInput = new ItemInput();
    assertNotNull(itemInput);
  }

  @Test
  public void testConstructor() {
    id = UUID.randomUUID();
    endingTime = new Date().toString();
    itemInput = new ItemInput(id, "name", "description", true,
            endingTime, new BigDecimal("2.2"), 1,
            "category", List.of("image"));
    assert itemInput.getId().equals(id);
    assert itemInput.getName().equals("name");
    assert itemInput.getDescription().equals("description");
    assert itemInput.getIsActive();
    assert itemInput.getEndingTime().equals(endingTime);
    assert itemInput.getPrice().equals(new BigDecimal("2.2"));
    assert itemInput.getStock().equals(1);
    assert itemInput.getCategory().equals("category");
    assert itemInput.getImages().contains("image");
  }

  @BeforeEach
  public void setUp() {
    id = UUID.randomUUID();
    endingTime = new Date().toString();
    itemInput = new ItemInput(id, "name", "description", true,
            endingTime, new BigDecimal("2.2"), 1,
            "category", List.of("image"));
  }

  @Test
  public void testNameMethods() {
    itemInput.setName("newName");
    assert itemInput.getName().equals("newName");
  }

  @Test
  public void testDescriptionMethods() {
    itemInput.setDescription("newDescription");
    assert itemInput.getDescription().equals("newDescription");
  }

  @Test
  public void testIsActiveMethods() {
    itemInput.setIsActive(false);
    assert !itemInput.getIsActive();
  }

  @Test
  public void testEndingTimeMethods() {
    itemInput.setEndingTime(endingTime);
    assert itemInput.getEndingTime().equals(endingTime);
  }

  @Test
  public void testPriceMethods() {
    itemInput.setPrice(new BigDecimal("2"));
    assert itemInput.getPrice().equals(new BigDecimal("2"));
  }

  @Test
  public void testStockMethods() {
    itemInput.setStock(2);
    assert itemInput.getStock().equals(2);
  }

  @Test
  public void testCategoryMethods() {
    itemInput.setCategory("newCategory");
    assert itemInput.getCategory().equals("newCategory");
  }

  @Test
  public void testImagesMethods() {
    itemInput.setImages(List.of("image2"));
    assert itemInput.getImages().contains("image2");
  }
}
