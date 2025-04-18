package backend.items.repositories;

import backend.items.entities.ItemEntity;
import jakarta.transaction.Transactional;
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
public interface ItemRepository extends JpaRepository<ItemEntity, UUID> {

  /**
   * This query creates or updates an item.
   *
   * @param itemId      The id of the item to update or insert.
   * @param name        The name of the item.
   * @param description The description of the item.
   * @param isActive    The active status of the item.
   * @param price       The price of the item.
   * @param stock       The stock quantity of the item.
   * @param category    The category of the item.
   * @param images      The images of the item.
   * @param sellerId    The id of the seller.
   */
  @Query(value = "SELECT * FROM insert_or_update_item(:itemId, :name, :description,"
          + ":isActive, :endingDate, :price, :stock, :category, CAST(:images AS jsonb), :sellerId)",
          nativeQuery = true)
  @Transactional
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
   * @param page       The page to search retrieve.
   * @param pageSize   The size of the page.
   *
   * @return A list of items that match the search text.
   */
  @Query(value = "SELECT * FROM search_for_items(:searchText, :order_by, "
          + "CAST(:order_direction AS sort_order_direction), :page, :pageSize)",
          nativeQuery = true)
  List<ItemEntity> searchForItems(@Param("searchText") String searchText,
                                  @Param("order_by") String orderBy,
                                  @Param("order_direction") String orderDirection,
                                  @Param("page") int page,
                                  @Param("pageSize") int pageSize);

  /**
   * This query gets all the items for a user.
   *
   * @param userId   The user id to search against.
   * @param isActive If the items are active or not.
   * @param page     The page of items to search for.
   * @param pageSize The size of the pages.
   *
   * @return The items for the user.
   */
  @Query(value = "SELECT * FROM get_items_by_user(:userId, :isActive, :page, :pageSize)",
          nativeQuery = true)
  List<ItemEntity> getUserItems(@Param("userId") UUID userId,
                                @Param("isActive") Boolean isActive,
                                @Param("page") int page,
                                @Param("pageSize") int pageSize);


  /**
   * This query gets the shop items.
   *
   * @param orderBy        What to order the items on.
   * @param orderDirection Which direction to order the items.
   * @param page           The current page.
   * @param pageSize       The size of the page.
   *
   * @return The shops items.
   */
  @Query(value = "SELECT * FROM get_shop_items(:order_by, "
          + "CAST(:order_direction AS sort_order_direction), :page, :pageSize)",
          nativeQuery = true)
  List<ItemEntity> getShopItems(@Param("order_by") String orderBy,
                                @Param("order_direction") String orderDirection,
                                @Param("page") int page,
                                @Param("pageSize") int pageSize);

  /**
   * This query gets the total amount of pages for a user.
   *
   * @param userId   The user id to search against.
   * @param isActive If the items are active.
   * @param pageSize The page size.
   *
   * @return The total amount of pages.
   */
  @Query(value = "SELECT * FROM get_items_by_user_pages(:userId, :isActive, :pageSize)",
          nativeQuery = true)
  int getUserItemsPages(@Param("userId") UUID userId,
                        @Param("isActive") Boolean isActive,
                        @Param("pageSize") int pageSize);


  /**
   * This query gets the total amount of pages for a search text.
   *
   * @param searchText The search text to match against item names.
   * @param pageSize   The size of the page.
   *
   * @return The total amount of pages for the search text.
   */
  @Query(value = "SELECT * FROM get_item_search_pages(:searchText, :pageSize)",
          nativeQuery = true)
  int getItemSearchPages(@Param("searchText") String searchText,
                         @Param("pageSize") int pageSize);

  /**
   * This query gets the total amount of pages for the shop page.
   *
   * @param pageSize The size of the page.
   *
   * @return The total amount of pages for the shop.
   */
  @Query(value = "SELECT * FROM get_shop_items_pages(:pageSize)",
          nativeQuery = true)
  int getShopItemsPages(@Param("pageSize") int pageSize);


  /**
   * This function gets the items price from the database.
   *
   * @param itemId the id of the item.
   *
   * @return the price.
   */
  @Query(value = "SELECT get_item_price(:itemId)", nativeQuery = true)
  BigDecimal getItemPriceByItemId(@Param("itemId") UUID itemId);

  /**
   * This query gets the items from finished auctions.
   *
   * @return the items.
   */

  @Query(value = "SELECT * FROM get_items_from_finished_auctions()", nativeQuery = true)
  @Transactional
  List<ItemEntity> getItemsFromFinishedAuctions();
}
