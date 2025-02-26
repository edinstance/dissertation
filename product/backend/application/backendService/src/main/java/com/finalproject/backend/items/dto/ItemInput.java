package com.finalproject.backend.items.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for user input.
 */
@Getter
@Setter
public class ItemInput {

  /**
   * The id of the item.
   * Optional.
   */
  private UUID id;

  /**
   * The name of the item.
   */
  private String name;

  /**
   * The description of the item.
   */
  private String description;

  /**
   * The active status of the item.
   */
  private Boolean isActive;

  /**
   * The ending date of the item.
   */
  private String endingTime;
  /**
   * The price of the item.
   */
  private BigDecimal price;

  /**
   * The stock quantity of the item.
   */
  private Integer stock;

  /**
   * The category of the item.
   */
  private String category;

  /**
   * The images of the item.
   */
  private List<String> images;

  /**
   * No-argument constructor.
   */
  public ItemInput() {
  }

  /**
   * Constructor to initialize all fields.
   *
   * @param inputItemId      The id of the item.
   * @param inputName        The name of the item.
   * @param inputDescription The description of the item.
   * @param inputIsActive    The active status of the item.
   * @param inputEndingTime  The ending date of the item.
   * @param inputPrice       The price of the item.
   * @param inputStock       The stock quantity of the item.
   * @param inputCategory    The category of the item.
   * @param inputImages      The images of the item.
   */
  public ItemInput(final UUID inputItemId, final String inputName, final String inputDescription,
                   final Boolean inputIsActive, final String inputEndingTime,
                   final BigDecimal inputPrice, final Integer inputStock,
                   final String inputCategory, final List<String> inputImages) {
    this.id = inputItemId;
    this.name = inputName;
    this.description = inputDescription;
    this.isActive = inputIsActive;
    this.endingTime = inputEndingTime;
    this.price = inputPrice;
    this.stock = inputStock;
    this.category = inputCategory;
    this.images = inputImages;
  }
}
