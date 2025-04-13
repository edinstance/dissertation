package backend.ItemsTests.QueryTests;

import backend.common.dto.PaginationInput;
import backend.common.helpers.Pagination;
import backend.common.helpers.Sorting;
import backend.items.dto.SearchedItemsResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetItemsByUserTests extends SetupQueryTests {

  @Test
  public void testGetItemsByUser() {
    PaginationInput paginationInput = new PaginationInput();
    when(itemService.getItemsByUser(id, true, paginationInput))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination()));

    SearchedItemsResponse result = itemQueries.getItemsByUser(id.toString(), true,
            paginationInput);

    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
  }

  @Test
  public void testGetItemsByUserNullInputs() {
    when(itemService.getItemsByUser(eq(id), eq(true), any(PaginationInput.class)))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination()));

    SearchedItemsResponse result = itemQueries.getItemsByUser(id.toString(), null, null);

    ArgumentCaptor<PaginationInput> paginationCaptor = ArgumentCaptor.forClass(PaginationInput.class);
    verify(itemService).getItemsByUser(eq(id), eq(true), paginationCaptor.capture());

    PaginationInput capturedPagination = paginationCaptor.getValue();
    assert capturedPagination.getSize() == 10;
    assert capturedPagination.getPage() == 0;
    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
  }

  @Test
  public void testGetItemsByUserPaginationSizeZero() {
    PaginationInput paginationInput = new PaginationInput(0, 0);
    when(itemService.getItemsByUser(eq(id), eq(true), any(PaginationInput.class)))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination(),
                    new Sorting()));

    SearchedItemsResponse result = itemQueries.getItemsByUser(id.toString(), true, paginationInput);

    ArgumentCaptor<PaginationInput> paginationCaptor = ArgumentCaptor.forClass(PaginationInput.class);
    verify(itemService).getItemsByUser(eq(id), eq(true), paginationCaptor.capture());

    PaginationInput capturedPagination = paginationCaptor.getValue();
    assert capturedPagination.getSize() == 10;
    assert capturedPagination.getPage() == 0;
    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
  }

  @Test
  public void testGetItemsByUserPaginationSizeNotZero() {
    PaginationInput paginationInput = new PaginationInput(0, 1);
    when(itemService.getItemsByUser(eq(id), eq(true), any(PaginationInput.class)))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination()));

    SearchedItemsResponse result = itemQueries.getItemsByUser(id.toString(), true, paginationInput);

    ArgumentCaptor<PaginationInput> paginationCaptor = ArgumentCaptor.forClass(PaginationInput.class);
    verify(itemService, times(1)).getItemsByUser(eq(id), eq(true), paginationCaptor.capture());

    PaginationInput capturedPagination = paginationCaptor.getValue();
    assert capturedPagination.getSize() == 1;
    assert capturedPagination.getPage() == 0;
    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
  }

}
