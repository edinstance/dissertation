package com.finalproject.backend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.config.logging.AppLogger;
import com.finalproject.backend.entities.ItemEntity;
import com.finalproject.backend.helpers.AuthHelpers;
import com.finalproject.backend.repositories.ItemRepository;
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
   * Constructs a ItemService with the specified ItemRepository.
   *
   * @param inputItemRepository The repository for accessing Item entities.
   *
   */
  @Autowired
  public ItemService(final ItemRepository inputItemRepository,
                     final JedisPool inputJedisPool,
                     final AuthHelpers authHelpers) {
    this.itemRepository = inputItemRepository;
    this.jedisPool = inputJedisPool;
    this.authHelpers = authHelpers;
  }

  /**
   * Retrieves an item entity by its ID.
   *
   * @param id The ID of the item.
   * @return The item entity.
   */
  public ItemEntity getItemById(UUID id) {

    try (Jedis jedis = jedisPool.getResource()) {

      String key = "item:" + id.toString();
      String cachedValueString = jedis.get(key);

      if (cachedValueString != null) {
        AppLogger.info("Found cached value: " + cachedValueString);
        jedis.expire(key, 300);
        return objectMapper.readValue(cachedValueString, ItemEntity.class);
      }

      ItemEntity item = itemRepository.findById(id).orElse(null);

      AppLogger.info("Found item in database: " + item);
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
   * @return The items found.
   */
  public List<ItemEntity> searchForItemsByName(final String searchText) {
    return itemRepository.searchForItems(searchText);
  }

  /**
   * This saves or updates an item in the database.
   *
   * @param itemEntity is the item to create or update.
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

    return itemRepository.saveOrUpdateItem(itemEntity.getId(), itemEntity.getName(),
            itemEntity.getDescription(), itemEntity.getIsActive(),
            new Timestamp(dateFormat.parse(itemEntity.getEndingTime()).getTime()),
            itemEntity.getPrice(), itemEntity.getStock(), itemEntity.getCategory(),
            objectMapper.writeValueAsString(itemEntity.getImages()),
            authHelpers.getCurrentUserId());
  }

}

