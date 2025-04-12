package com.finalproject.backend.ItemsTests.dtoTests;

import com.finalproject.backend.common.helpers.Pagination;
import com.finalproject.backend.common.helpers.Sorting;
import com.finalproject.backend.items.dto.SearchedItemsResponse;
import com.finalproject.backend.items.entities.ItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SearchedItemsResponseTests {

  private final ItemEntity item = new ItemEntity();
  private final Pagination pagination = new Pagination();
  private final Sorting sorting = new Sorting();
  private SearchedItemsResponse searchedItemsResponse;

  @Test
  public void searchedItemsResponseDefaultConstructorTest() {
    searchedItemsResponse = new SearchedItemsResponse();

    assertNotNull(searchedItemsResponse);
  }

  @Test
  public void searchedItemsResponseConstructorTest() {
    searchedItemsResponse = new SearchedItemsResponse(List.of(item), pagination);

    assertNotNull(searchedItemsResponse);
  }

  @Test
  public void searchedItemsResponseSortingConstructorTest() {
    searchedItemsResponse = new SearchedItemsResponse(List.of(item), pagination, sorting);

    assertNotNull(searchedItemsResponse);
  }

  @BeforeEach
  public void setUp() {
    searchedItemsResponse = new SearchedItemsResponse(List.of(item), pagination, sorting);
  }

  @Test
  public void itemsMethodsTest() {
    List<ItemEntity> items = List.of(item);
    searchedItemsResponse.setItems(items);

    assert searchedItemsResponse.getItems().getFirst().equals(item);
  }

  @Test
  public void paginationMethodsTest() {
    searchedItemsResponse.setPagination(pagination);

    assert searchedItemsResponse.getPagination().equals(pagination);
  }

  @Test
  public void sortingMethodsTest() {
    searchedItemsResponse.setSorting(sorting);
    assert searchedItemsResponse.getSorting().equals(sorting);
  }
}
