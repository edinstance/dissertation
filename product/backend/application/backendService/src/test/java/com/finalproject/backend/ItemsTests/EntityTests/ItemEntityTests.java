package com.finalproject.backend.ItemsTests.EntityTests;

import com.finalproject.backend.items.entities.ItemEntity;
import com.finalproject.backend.users.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ItemEntityTests {

  private ItemEntity itemEntity;
  private UserEntity userEntity;

  @BeforeEach
  public void setUp() {
    userEntity = new UserEntity(UUID.randomUUID(), "seller@test.com", "Seller Name");
    itemEntity = new ItemEntity(UUID.randomUUID(), "Item Name", "Item Description", new Date().toString(), new BigDecimal("19.99"), 100, "Category", List.of("image1"), userEntity);
  }

  @Test
  public void testDefaultConstructor() {
    itemEntity = new ItemEntity();

    assertNotNull(itemEntity);
  }

  @Test
  public void testConstructor() {
    Date date = new Date();
    itemEntity = new ItemEntity(UUID.randomUUID(), "Item Name", "Item Description", date.toString(), new BigDecimal("29.99"), 100, "Category", List.of("image1"), userEntity);

    assertNotNull(itemEntity);
    assertNotNull(itemEntity.getId());
    assertEquals("Item Name", itemEntity.getName());
    assertEquals("Item Description", itemEntity.getDescription());
    assertEquals(date.toString(), itemEntity.getEndingTime());
    assertEquals(true, itemEntity.getIsActive());
    assertEquals(new BigDecimal("29.99"), itemEntity.getPrice());
    assertEquals(100, itemEntity.getStock());
    assertEquals("Category", itemEntity.getCategory());
    assertEquals(List.of("image1"), itemEntity.getImages());
    assertEquals(userEntity, itemEntity.getSeller());
  }

  @Test
  public void testIdMethods() {
    UUID itemId = UUID.randomUUID();
    itemEntity.setId(itemId);
    assertEquals(itemId, itemEntity.getId());
  }

  @Test
  public void testNameMethods() {
    itemEntity.setName("New Item Name");
    assertEquals("New Item Name", itemEntity.getName());
  }

  @Test
  public void testDescriptionMethods() {
    itemEntity.setDescription("New Item Description");
    assertEquals("New Item Description", itemEntity.getDescription());
  }

  @Test
  public void testIsActiveMethods() {
    assert itemEntity.getIsActive();
    itemEntity.setIsActive(false);
    assertEquals(false, itemEntity.getIsActive());
  }

  @Test
  public void testEndingTimeMethods() {
    Date date = new Date();
    itemEntity.setEndingTime(date.toString());
    assertEquals(date.toString(), itemEntity.getEndingTime());
  }

  @Test
  public void testPriceMethods() {
    itemEntity.setPrice(new BigDecimal("29.99"));
    assertEquals(new BigDecimal("29.99"), itemEntity.getPrice());
  }

  @Test
  public void testStockMethods() {
    itemEntity.setStock(200);
    assertEquals(200, itemEntity.getStock());
  }

  @Test
  public void testCategoryMethods() {
    itemEntity.setCategory("New Category");
    assertEquals("New Category", itemEntity.getCategory());
  }

  @Test
  public void testImagesMethods() {
    itemEntity.setImages(List.of("image2"));
    assertEquals(List.of("image2"), itemEntity.getImages());
  }

  @Test
  public void testSellerMethods() {
    UserEntity newSeller = new UserEntity(UUID.randomUUID(), "new_seller@test.com", "New Seller");
    itemEntity.setSeller(newSeller);
    assertEquals(newSeller, itemEntity.getSeller());
  }

  @Test
  public void testToString() {
    UUID itemId = UUID.randomUUID();
    Date date = new Date();
    UserEntity seller = new UserEntity(UUID.randomUUID(), "seller@test.com", "Seller Name");
    ItemEntity item = new ItemEntity(itemId, "Item Name", "Item Description", date.toString(), new BigDecimal("19.99"), 100, "Category", List.of("image1"), seller);

    String expected = "ItemEntity{" +
            "itemId=" + itemId +
            ", name='Item Name'" +
            ", description='Item Description'" +
            ", isActive=true" +
            ", endingDate=" + date +
            ", price=19.99" +
            ", stock=100" +
            ", category='Category'" +
            ", images=[image1]" +
            ", seller=" + seller +
            '}';

    assertEquals(expected, item.toString());
  }
}