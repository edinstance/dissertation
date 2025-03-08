package com.finalproject.backend.ItemsTests.ServiceTests;

import com.finalproject.backend.common.dto.PaginationInput;
import com.finalproject.backend.common.dto.SortInput;
import com.finalproject.backend.common.types.SortDirection;
import com.finalproject.backend.items.dto.SearchedItemsResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SearchForItemsTests extends  SetupServiceTests {

  @Test
  public void testSearchForItems() {
    when(itemRepository.searchForItems("Item Name", sortInput.getSortBy(), sortInput.getSortDirection(),
            paginationInput.getPage(), paginationInput.getSize())).thenReturn(List.of(item));

    assert itemService.searchForItemsByName("Item Name", paginationInput, sortInput).getItems().contains(item);
    verify(itemRepository).searchForItems("Item Name", "name", SortDirection.ASC,0, 0);
  }

  @Test
  public void testCustomSearchPagination(){
    when(itemRepository.searchForItems("Item Name", sortInput.getSortBy(), sortInput.getSortDirection(), 2, 3)).thenReturn(List.of(item));

    SearchedItemsResponse searchedItemsResponse = itemService.searchForItemsByName("Item Name", new PaginationInput(2, 3), sortInput);

    assertEquals(searchedItemsResponse.getItems().getFirst(), item);
    verify(itemRepository, times(1)).searchForItems("Item Name", "name", SortDirection.ASC, 2, 3);
  }

  @Test
  public void testCustomSearchSorting(){
    when(itemRepository.searchForItems("Item Name", "price", SortDirection.DESC, 2, 3)).thenReturn(List.of(item));

    SearchedItemsResponse searchedItemsResponse = itemService.searchForItemsByName("Item Name", new PaginationInput(2, 3), new SortInput("price", SortDirection.DESC));

    assertEquals(searchedItemsResponse.getItems().getFirst(), item);
    verify(itemRepository, times(1)).searchForItems("Item Name", "price", SortDirection.DESC, 2, 3);
  }
}
