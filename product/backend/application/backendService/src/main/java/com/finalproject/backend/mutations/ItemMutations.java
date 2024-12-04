package com.finalproject.backend.mutations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.finalproject.backend.dto.ItemInput;
import com.finalproject.backend.dto.UserDetailsInput;
import com.finalproject.backend.entities.ItemEntity;
import com.finalproject.backend.entities.UserDetailsEntity;
import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.mappers.ItemMapper;
import com.finalproject.backend.mappers.UserMapper;
import com.finalproject.backend.repositories.ItemRepository;
import com.finalproject.backend.services.ItemService;
import com.finalproject.backend.services.UserService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.UUID;

/**
 * GraphQL mutations for item-related operations.
 */
@DgsComponent
public class ItemMutations {

  /**
   * The service the mutation will interact with.
   */
  private final ItemService itemService;

  /**
   * The mapper the mutation will use
   */
  private final ItemMapper itemMapper;
  /**
   * Constructor for initialising the UserMutations with the input services.
   *
   * @param inputItemService The item service to be used by this component.
   * @param inputItemMapper The itemMapper to be used.
   */
  public ItemMutations(final ItemService inputItemService,
                       final ItemMapper inputItemMapper) {
    this.itemService = inputItemService;
    this.itemMapper = inputItemMapper;
  }

  /**
   * GraphQL mutation to create or update an item.
   *
   * @param itemInput The input data for the item.
   */
  @DgsMutation
  public ItemEntity saveItem(
          @InputArgument ItemInput itemInput) throws JsonProcessingException, ParseException {
    ItemEntity item = itemMapper.mapInputToItem(itemInput);
    return itemService.saveOrUpdateItem(item);
  }
}