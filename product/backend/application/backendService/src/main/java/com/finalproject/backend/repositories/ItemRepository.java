package com.finalproject.backend.repositories;

import com.finalproject.backend.entities.ItemEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for managing item entities.
 */
public interface ItemRepository  extends JpaRepository<ItemEntity, UUID> {

  /**
   * This query creates or updates an item.
   *
   * @param itemId The id of the item to update or insert.
   * @param name The name of the item.
   * @param description The description of the item.
   * @param isActive The active status of the item.
   * @param price The price of the item.
   * @param stock The stock quantity of the item.
   * @param category The category of the item.
   * @param images The images of the item.
   * @param sellerId The id of the seller.
   */
  @Query(value = "SELECT * FROM insert_or_update_item(:itemId, :name, :description,"
          + ":isActive, :endingDate, :price, :stock, :category, CAST(:images AS jsonb), :sellerId)",
          nativeQuery = true)
  ItemEntity saveOrUpdateItem(
          @Param("itemId") UUID itemId,
          @Param("name") String name,
          @Param("description") String description,
          @Param("isActive") Boolean isActive,
          @Param("endingDate") Date endingDate,
          @Param("price") BigDecimal price,
          @Param("stock") Integer stock,
          @Param("category") String category,
          @Param("images") String images,
          @Param("sellerId") UUID sellerId
  );

  /**
   * This query gets items based on the similarity of the item name and the search text.
   *
   * @param searchText The search text to match against item names.
   * @return A list of items that match the search text.
   */
  @Query(value = "SELECT * FROM search_for_items(:searchText)",
          nativeQuery = true)
  List<ItemEntity> searchForItems(@Param("searchText") String searchText);
}
