package com.finalproject.backend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.entities.ItemEntity;
import com.finalproject.backend.repositories.ItemRepository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service class for managing User entities.
 */
@Service
public class ItemService {

  /**
   * Object mapper for mapping to to json.
   */
  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Repository for accessing item data.
   */
  private final ItemRepository itemRepository;

  /**
   * Constructs a ItemService with the specified ItemRepository.
   *
   * @param inputItemRepository The repository for accessing Item entities.
   */
  @Autowired
  public ItemService(final ItemRepository inputItemRepository) {
    this.itemRepository = inputItemRepository;
  }



  /**
   * Retrieves an item entity by its ID.
   *
   * @param id The ID of the item.
   * @return The item entity.
   */
  public ItemEntity getItemById(UUID id) {
    return itemRepository.findById(id).orElse(null);
  }

  /**
   * Searches for items based on an input name.
   *
   * @param inputName The name to search against.
   * @return The items found.
   */
  public List<ItemEntity> searchForItemsByName(final String inputName) {
    return itemRepository.searchForItems(inputName);
  }

  /**
   * This saves or updates an item in the database.
   *
   * @param itemEntity is the item to create or update.
   * @return the item is returned.
   */
  public ItemEntity saveOrUpdateItem(final ItemEntity itemEntity) throws JsonProcessingException,
          ParseException {
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return itemRepository.saveOrUpdateItem(itemEntity.getId(), itemEntity.getName(),
            itemEntity.getDescription(), itemEntity.getIsActive(),
            new Timestamp(dateFormat.parse(itemEntity.getEndingTime()).getTime()),
            itemEntity.getPrice(), itemEntity.getStock(), itemEntity.getCategory(),
            objectMapper.writeValueAsString(itemEntity.getImages()), itemEntity.getSeller().getId());
  }

}

