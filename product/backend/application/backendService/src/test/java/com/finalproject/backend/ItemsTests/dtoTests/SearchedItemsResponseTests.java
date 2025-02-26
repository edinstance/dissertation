package com.finalproject.backend.ItemsTests.dtoTests;

import com.finalproject.backend.items.dto.SearchedItemsResponse;
import com.finalproject.backend.items.entities.ItemEntity;
import com.finalproject.backend.common.helpers.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SearchedItemsResponseTests{

  private SearchedItemsResponse searchedItemsResponse;
  private final ItemEntity item = new ItemEntity();
  private final Pagination pagination = new Pagination();

  @Test
  public void searchedItemsResponseDefaultConstructorTest(){
    searchedItemsResponse = new SearchedItemsResponse();

    assertNotNull(searchedItemsResponse);
  }

  @Test
  public void searchedItemsResponseConstructorTest(){
    searchedItemsResponse = new SearchedItemsResponse(List.of(item), pagination);

    assertNotNull(searchedItemsResponse);
  }

  @BeforeEach
  public void setUp(){
    searchedItemsResponse = new SearchedItemsResponse(List.of(item), pagination);
  }

  @Test
  public void itemsMethodsTest(){
    List<ItemEntity> items = List.of(item);
    searchedItemsResponse.setItems(items);

    assert searchedItemsResponse.getItems().getFirst().equals(item);
  }

  @Test
  public void paginationMethodsTest(){
    searchedItemsResponse.setPagination(pagination);

    assert searchedItemsResponse.getPagination().equals(pagination);
  }
}
