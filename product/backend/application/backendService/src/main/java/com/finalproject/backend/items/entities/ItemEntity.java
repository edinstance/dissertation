package com.finalproject.backend.items.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finalproject.backend.common.converters.StringListToJsonbConverter;
import com.finalproject.backend.common.converters.TimestampToStringConverter;
import com.finalproject.backend.users.entities.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an item entity with basic information.
 */
@Entity
@Getter
@Setter
@Table(name = "items")
public class ItemEntity {

  /**
   * The item's id.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "item_id", unique = true, nullable = false)
  private UUID id;

  /**
   * The item's name.
   */
  @Column(name = "name", nullable = false)
  private String name;

  /**
   * The item's description.
   */
  @Column(name = "description")
  private String description;

  /**
   * The item's active status.
   */
  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;

  /**
   * The item's ending date.
   */
  @Convert(converter = TimestampToStringConverter.class)
  @Column(name = "ending_time", nullable = false)
  private String endingTime;

  /**
   * The item's price.
   */
  @Column(name = "price", nullable = false)
  private BigDecimal price;

  /**
   * The item's stock quantity.
   */
  @Column(name = "stock", nullable = false)
  private Integer stock;

  /**
   * The item's category.
   */
  @Column(name = "category")
  private String category;

  /**
   * The item's images.
   */
  @Convert(converter = StringListToJsonbConverter.class)
  @Column(name = "images", columnDefinition = "jsonb")
  private List<String> images;

  /**
   * The item's seller.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "seller_id", nullable = false)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private UserEntity seller;

  /**
   * Default constructor.
   */
  public ItemEntity() {
  }

  /**
   * This constructor creates a new ItemEntity with specified details.
   *
   * @param id        The item's id.
   * @param name          The item's name.
   * @param description   The item's description.
   * @param endingTime    The item's ending date.
   * @param price         The item's price.
   * @param stock         The item's stock quantity.
   * @param category      The item's category.
   * @param images        The item's images.
   * @param seller        The item's seller.
   */
  public ItemEntity(UUID id, String name, String description,
                    String endingTime, BigDecimal price, Integer stock,
                    String category, List<String> images, UserEntity seller) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.endingTime = endingTime;
    this.price = price;
    this.stock = stock;
    this.category = category;
    this.images = images;
    this.seller = seller;
  }

  @Override
  public String toString() {
    return "ItemEntity{"
            + "itemId=" + id
            + ", name='" + name + '\''
            + ", description='" + description + '\''
            + ", isActive=" + isActive
            + ", endingDate=" + endingTime
            + ", price=" + price
            + ", stock=" + stock
            + ", category='" + category + '\''
            + ", images=" + images
            + ", seller=" + seller
            + '}';
  }
}