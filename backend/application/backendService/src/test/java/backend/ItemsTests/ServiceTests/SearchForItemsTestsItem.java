package backend.ItemsTests.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.common.dto.PaginationInput;
import backend.common.dto.SortInput;
import backend.common.types.SortDirection;
import backend.items.dto.SearchedItemsResponse;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SearchForItemsTestsItem extends SetupItemServiceTests {

  @Test
  public void testSearchForItems() {
    when(itemRepository.searchForItems("Item Name", sortInput.getSortBy(), sortInput.getSortDirection().name(),
            paginationInput.getPage(), paginationInput.getSize())).thenReturn(List.of(item));

    assert itemService.searchForItemsByName("Item Name", paginationInput, sortInput).getItems().contains(item);
    verify(itemRepository).searchForItems("Item Name", "name", SortDirection.ASC.name(), 0, 0);
  }

  @Test
  public void testCustomSearchPagination() {
    when(itemRepository.searchForItems("Item Name", sortInput.getSortBy(), sortInput.getSortDirection().name(), 2, 3)).thenReturn(List.of(item));

    SearchedItemsResponse searchedItemsResponse = itemService.searchForItemsByName("Item Name", new PaginationInput(2, 3), sortInput);

    assertEquals(searchedItemsResponse.getItems().getFirst(), item);
    verify(itemRepository, times(1)).searchForItems("Item Name", "name", SortDirection.ASC.name(), 2, 3);
  }

  @Test
  public void testCustomSearchSorting() {
    when(itemRepository.searchForItems("Item Name", "price", SortDirection.DESC.name(), 2, 3)).thenReturn(List.of(item));

    SearchedItemsResponse searchedItemsResponse = itemService.searchForItemsByName("Item Name", new PaginationInput(2, 3), new SortInput("price", SortDirection.DESC));

    assertEquals(searchedItemsResponse.getItems().getFirst(), item);
    verify(itemRepository, times(1)).searchForItems("Item Name", "price", SortDirection.DESC.name(), 2, 3);
  }
}
