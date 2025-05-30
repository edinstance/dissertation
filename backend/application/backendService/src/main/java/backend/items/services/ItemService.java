package backend.items.services;

import backend.common.config.logging.AppLogger;
import backend.common.dto.PaginationInput;
import backend.common.dto.SortInput;
import backend.common.helpers.AuthHelpers;
import backend.common.helpers.Pagination;
import backend.common.helpers.Sorting;
import backend.items.dto.SearchedItemsResponse;
import backend.items.entities.ItemEntity;
import backend.items.helpers.ItemCacheHelpers;
import backend.items.repositories.ItemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

/**
 * Service class for managing User entities.
 */
@Service
public class ItemService {

  /**
   * Object mapper for mapping to json.
   */
  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Repository for accessing item data.
   */
  private final ItemRepository itemRepository;

  /**
   * Pool for accessing redis.
   */
  private final JedisPool jedisPool;

  /**
   * The auth helpers to use.
   */
  private final AuthHelpers authHelpers;

  /**
   * The item cache helper to use.
   */
  private final ItemCacheHelpers itemCacheHelpers;

  /**
   * Constructs a ItemService with the specified ItemRepository.
   *
   * @param inputItemRepository The repository for accessing Item entities.
   */
  @Autowired
  public ItemService(final ItemRepository inputItemRepository,
                     final JedisPool inputJedisPool,
                     final AuthHelpers authHelpers,
                     final ItemCacheHelpers itemCacheHelpers) {
    this.itemRepository = inputItemRepository;
    this.jedisPool = inputJedisPool;
    this.authHelpers = authHelpers;
    this.itemCacheHelpers = itemCacheHelpers;
  }

  /**
   * Retrieves an item entity by its ID.
   *
   * @param id The ID of the item.
   *
   * @return The item entity.
   */
  public ItemEntity getItemById(UUID id) {

    try (Jedis jedis = jedisPool.getResource()) {

      String key = "item:" + id.toString();
      String cachedValueString = jedis.get(key);

      if (cachedValueString != null) {
        AppLogger.info("Found item " + id + " in cache");

        jedis.expire(key, 300);
        return objectMapper.readValue(cachedValueString, ItemEntity.class);
      }

      ItemEntity item = itemRepository.findById(id).orElse(null);

      AppLogger.info("Found item " + id + " in database");

      if (item != null) {
        jedis.set("item:" + item.getId(), objectMapper.writeValueAsString(item),
                SetParams.setParams().ex(300));
      }

      return item;

    } catch (JsonProcessingException e) {
      AppLogger.error("Error while finding item: " + id, e);
      throw new RuntimeException(e);
    }
  }

  /**
   * Searches for items based on an input name.
   *
   * @param searchText The name to search against.
   *
   * @return The items found and the pagination information.
   */
  public SearchedItemsResponse searchForItemsByName(
          final String searchText,
          final PaginationInput pagination, final SortInput sort
  ) {
    return new SearchedItemsResponse(itemRepository.searchForItems(searchText,
            sort.getSortBy(), sort.getSortDirection().name(),
            pagination.getPage(), pagination.getSize()),
            new Pagination(pagination.getPage(), pagination.getSize(),
                    itemRepository.getItemSearchPages(searchText,
                            pagination.getSize())));
  }

  /**
   * This query gets all the items for a user.
   *
   * @param userId     The id of the user.
   * @param isActive   If the items are active or not.
   * @param pagination The pagination data for the query.
   *
   * @return The items and pagination data.
   */
  public SearchedItemsResponse getItemsByUser(final UUID userId,
                                              final Boolean isActive,
                                              final PaginationInput pagination) {
    List<ItemEntity> items;
    String key = "user:" + userId + ":items:page:" + pagination.getPage() + "active:" + isActive;
    try (Jedis jedis = jedisPool.getResource()) {
      String cachedItems = jedis.get(key);
      if (cachedItems != null) {

        AppLogger.info("Found user " + userId + " items in cache");

        items = objectMapper.readValue(cachedItems,
                new TypeReference<>() {}
        );

        jedis.expire(key, 300);
      } else {
        items = itemRepository.getUserItems(userId, isActive,
                pagination.getPage(), pagination.getSize());

        AppLogger.info("Found user " + userId + " items in database");

        jedis.set(key, objectMapper.writeValueAsString(items),
                SetParams.setParams().ex(300));
      }
    } catch (JsonProcessingException e) {
      AppLogger.error("Error while finding items for " + userId, e);
      throw new RuntimeException(e);
    }

    return new SearchedItemsResponse(items, new Pagination(pagination.getPage(),
            pagination.getSize(), itemRepository.getUserItemsPages(userId,
            isActive, pagination.getSize())));

  }

  /**
   * This function gets the shop items and returns them.
   *
   * @param pagination The pagination information.
   * @param sortInput  The sort information.
   *
   * @return The shop items.
   */
  public SearchedItemsResponse getShopItems(final PaginationInput pagination,
                                            final SortInput sortInput) {
    List<ItemEntity> items = itemRepository.getShopItems(sortInput.getSortBy(),
            sortInput.getSortDirection().name(),
            pagination.getPage(), pagination.getSize());

    AppLogger.info("Retrieved " + items.size() + " items for the shop");

    return new SearchedItemsResponse(items, new Pagination(pagination.getPage(),
            pagination.getSize(), itemRepository.getShopItemsPages(pagination.getSize())),
            new Sorting(sortInput.getSortBy(), sortInput.getSortDirection()));

  }

  /**
   * This saves or updates an item in the database.
   *
   * @param itemEntity is the item to create or update.
   *
   * @return the item is returned.
   */
  public ItemEntity saveOrUpdateItem(
          final ItemEntity itemEntity)
          throws JsonProcessingException, ParseException {
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    try (Jedis jedis = jedisPool.getResource()) {
      jedis.set("item:" + itemEntity.getId(),
              objectMapper.writeValueAsString(itemEntity),
              SetParams.setParams().ex(300));
    }

    AppLogger.info("Updating item: " + itemEntity);

    itemCacheHelpers.invalidateUserItems(authHelpers.getCurrentUserId());

    return itemRepository.saveOrUpdateItem(itemEntity.getId(), itemEntity.getName(),
            itemEntity.getDescription(), itemEntity.getIsActive(),
            new Timestamp(dateFormat.parse(itemEntity.getEndingTime()).getTime()),
            itemEntity.getPrice(), itemEntity.getStock(), itemEntity.getCategory(),
            objectMapper.writeValueAsString(itemEntity.getImages()),
            authHelpers.getCurrentUserId());
  }

  /**
   * Function to get the users won items.
   *
   * @param pagination the pagnitation input.
   *
   * @return the users won items.
   */
  public SearchedItemsResponse getUsersWonItems(final PaginationInput pagination) {
    List<ItemEntity> items;

    UUID userId = authHelpers.getCurrentUserId();

    String key = "user:" + userId + ":won:items:page:" + pagination.getPage();
    try (Jedis jedis = jedisPool.getResource()) {
      String cachedItems = jedis.get(key);
      if (cachedItems != null) {

        AppLogger.info("Found user " + userId + " items in cache");

        items = objectMapper.readValue(cachedItems,
                new TypeReference<>() {}
        );

        jedis.expire(key, 300);
      } else {
        items = itemRepository.getUsersWonItems(userId,
                pagination.getPage(), pagination.getSize());

        AppLogger.info("Found user " + userId + " items in database");

        jedis.set(key, objectMapper.writeValueAsString(items),
                SetParams.setParams().ex(300));
      }
    } catch (JsonProcessingException e) {
      AppLogger.error("Error while finding items for " + userId, e);
      throw new RuntimeException(e);
    }
    AppLogger.info("Retrieved " + items.size() + " items for the user " + userId);

    return new SearchedItemsResponse(items, new Pagination(pagination.getPage(),
            pagination.getSize(),
            itemRepository.getUsersWonItemsPages(userId, pagination.getSize())));

  }

}

