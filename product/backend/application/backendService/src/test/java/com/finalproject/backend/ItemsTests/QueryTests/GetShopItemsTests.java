package com.finalproject.backend.ItemsTests.QueryTests;

import com.finalproject.backend.common.dto.PaginationInput;
import com.finalproject.backend.common.dto.SortInput;
import com.finalproject.backend.common.helpers.Pagination;
import com.finalproject.backend.common.helpers.Sorting;
import com.finalproject.backend.common.types.SortDirection;
import com.finalproject.backend.items.dto.SearchedItemsResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class GetShopItemsTests extends SetupQueryTests {

  @Test
  public void testGetShopItems() {
    PaginationInput paginationInput = new PaginationInput();
    SortInput sortInput = new SortInput();
    when(itemService.getShopItems( paginationInput, sortInput))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination(),
                    new Sorting()));

    SearchedItemsResponse result = itemQueries.getShopItems(paginationInput, sortInput);

    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
  }

  @Test
  public void testGetShopItemsNullInputs() {

    when(itemService.getShopItems( any(PaginationInput.class), any(SortInput.class)))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination(), new Sorting()));

    SearchedItemsResponse result = itemQueries.getShopItems(null, null);

    ArgumentCaptor<PaginationInput> paginationCaptor = ArgumentCaptor.forClass(PaginationInput.class);
    ArgumentCaptor<SortInput> sortCaptor = ArgumentCaptor.forClass(SortInput.class);
    verify(itemService, times(1)).getShopItems( paginationCaptor.capture(), sortCaptor.capture());

    assert paginationCaptor.getValue().getPage() == 0;
    assert paginationCaptor.getValue().getSize() == 10;
    assert sortCaptor.getValue().getSortBy().equals("ending_time");
    assert sortCaptor.getValue().getSortDirection() == SortDirection.ASC;
    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
  }

  @Test
  public void testGetShopItemsPaginationSizeZero() {

    when(itemService.getShopItems( any(PaginationInput.class), any(SortInput.class)))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination(),
                    new Sorting()));

    SearchedItemsResponse result = itemQueries.getShopItems(new PaginationInput(0,0), null);

    ArgumentCaptor<PaginationInput> paginationCaptor = ArgumentCaptor.forClass(PaginationInput.class);
    verify(itemService, times(1)).getShopItems( paginationCaptor.capture(), any(SortInput.class));

    assert paginationCaptor.getValue().getPage() == 0;
    assert paginationCaptor.getValue().getSize() == 10;
    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
  }

  @Test
  public void testGetShopItemsPaginationSizeNotZero() {
    PaginationInput paginationInput = new PaginationInput(0, 1);
    when(itemService.getShopItems( any(PaginationInput.class), any(SortInput.class)))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination(0,1,10),
                    new Sorting("ending_time", SortDirection.ASC)));

    SearchedItemsResponse result = itemQueries.getShopItems(paginationInput, null);

    ArgumentCaptor<PaginationInput> paginationCaptor = ArgumentCaptor.forClass(PaginationInput.class);
    verify(itemService, times(1)).getShopItems( paginationCaptor.capture(), any(SortInput.class));

    assert paginationCaptor.getValue().getPage() == 0;
    assert paginationCaptor.getValue().getSize() == 1;
    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
  }

  @Test
  public void testGetShopItemsSortResult() {
    PaginationInput paginationInput = new PaginationInput();
    SortInput sortInput = new SortInput();
    when(itemService.getShopItems( paginationInput, sortInput))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination(),
                    new Sorting("ending_time", SortDirection.ASC)));

    SearchedItemsResponse result = itemQueries.getShopItems(paginationInput, sortInput);

    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
    assert result.getSorting().getSortBy().equals("ending_time");
    assert result.getSorting().getSortDirection() == SortDirection.ASC;
  }
}
