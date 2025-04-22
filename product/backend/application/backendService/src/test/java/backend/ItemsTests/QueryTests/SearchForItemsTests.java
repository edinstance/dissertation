package backend.ItemsTests.QueryTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.common.dto.PaginationInput;
import backend.common.dto.SortInput;
import backend.common.helpers.Pagination;
import backend.common.helpers.Sorting;
import backend.common.types.SortDirection;
import backend.items.dto.SearchedItemsResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class SearchForItemsTests extends SetupQueryTests {

  @Test
  public void testSearchForItem() {
    PaginationInput paginationInput = new PaginationInput();
    SortInput sortInput = new SortInput();
    when(itemService.searchForItemsByName("name", paginationInput, sortInput))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination(), new Sorting()));

    SearchedItemsResponse result = itemQueries.searchForItems("name",
            sortInput, paginationInput);

    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
  }

  @Test
  public void testSearchForItemsNullInputs() {
    when(itemService.searchForItemsByName(eq("name"), any(PaginationInput.class), any(SortInput.class)))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination(0, 10, 1)));

    SearchedItemsResponse result = itemQueries.searchForItems("name", null, null);

    ArgumentCaptor<PaginationInput> paginationCaptor = ArgumentCaptor.forClass(PaginationInput.class);
    ArgumentCaptor<SortInput> sortCaptor = ArgumentCaptor.forClass(SortInput.class);
    verify(itemService).searchForItemsByName(eq("name"), paginationCaptor.capture(), sortCaptor.capture());

    PaginationInput capturedPagination = paginationCaptor.getValue();
    SortInput capturedSort = sortCaptor.getValue();
    assert capturedPagination.getPage() == 0;
    assert capturedPagination.getSize() == 10;
    assert capturedSort.getSortDirection() == SortDirection.ASC;
    assert capturedSort.getSortBy().equals("ending_time");
    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
    assert result.getPagination().getPage() == 0;
    assert result.getPagination().getSize() == 10;
  }

  @Test
  public void testSearchForItemsPaginationSizeZero() {
    when(itemService.searchForItemsByName(eq("name"), any(PaginationInput.class), any(SortInput.class)))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination(0, 10, 1)));

    SearchedItemsResponse result = itemQueries.searchForItems("name", new SortInput(), new PaginationInput(0, 0));

    ArgumentCaptor<PaginationInput> paginationCaptor = ArgumentCaptor.forClass(PaginationInput.class);
    verify(itemService).searchForItemsByName(eq("name"), paginationCaptor.capture(), any(SortInput.class));

    PaginationInput capturedPagination = paginationCaptor.getValue();
    assert capturedPagination.getPage() == 0;
    assert capturedPagination.getSize() == 10;
    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
    assert result.getPagination().getPage() == 0;
    assert result.getPagination().getSize() == 10;
  }

  @Test
  public void testSearchForItemsPaginationSizeNotZero() {
    when(itemService.searchForItemsByName(eq("name"), any(PaginationInput.class), any(SortInput.class)))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination(0, 12, 1)));

    SearchedItemsResponse result = itemQueries.searchForItems("name", new SortInput(), new PaginationInput(0, 12));

    ArgumentCaptor<PaginationInput> paginationCaptor = ArgumentCaptor.forClass(PaginationInput.class);
    verify(itemService).searchForItemsByName(eq("name"), paginationCaptor.capture(), any(SortInput.class));

    PaginationInput capturedPagination = paginationCaptor.getValue();
    assert capturedPagination.getPage() == 0;
    assert capturedPagination.getSize() == 12;
    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
    assert result.getPagination().getPage() == 0;
    assert result.getPagination().getSize() == 12;
  }

  @Test
  public void testSearchForItemsSorting() {
    PaginationInput paginationInput = new PaginationInput();
    SortInput sortInput = new SortInput("price", SortDirection.ASC);
    when(itemService.searchForItemsByName("name", paginationInput, sortInput))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination(), new Sorting("price", SortDirection.ASC)));

    SearchedItemsResponse result = itemQueries.searchForItems("name",
            sortInput, paginationInput);

    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
    assert result.getSorting().getSortBy().equals("price");
    assert result.getSorting().getSortDirection() == SortDirection.ASC;
  }

  @Test
  public void testSearchForItemsSortingNull() {
    when(itemService.searchForItemsByName(eq("name"), any(PaginationInput.class), any(SortInput.class)))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination(0, 12, 1), new Sorting()));

    SearchedItemsResponse result = itemQueries.searchForItems("name", null, new PaginationInput(0, 12));

    ArgumentCaptor<SortInput> sortCaptor = ArgumentCaptor.forClass(SortInput.class);
    verify(itemService).searchForItemsByName(eq("name"), any(PaginationInput.class), sortCaptor.capture());

    SortInput capturedSort = sortCaptor.getValue();
    assert capturedSort.getSortDirection() == SortDirection.ASC;
    assert capturedSort.getSortBy().equals("ending_time");
    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
    assert result.getPagination().getPage() == 0;
    assert result.getPagination().getSize() == 12;
  }

}
