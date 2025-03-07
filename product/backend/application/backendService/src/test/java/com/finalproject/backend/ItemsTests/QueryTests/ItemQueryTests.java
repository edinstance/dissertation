package com.finalproject.backend.ItemsTests.QueryTests;

import com.finalproject.backend.common.dto.PaginationInput;
import com.finalproject.backend.common.helpers.Pagination;
import com.finalproject.backend.items.dto.SearchedItemsResponse;
import com.finalproject.backend.items.entities.ItemEntity;
import com.finalproject.backend.items.queries.ItemQueries;
import com.finalproject.backend.items.services.ItemService;
import com.finalproject.backend.users.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemQueryTests {

  @Mock
  ItemService itemService;

  @InjectMocks
  ItemQueries itemQueries;
  private ItemEntity item;

  private UUID id;

  @BeforeEach
  void setUp() {
    UserEntity user = new UserEntity(UUID.randomUUID(), "seller@test.com",
            "Seller Name");

    id = UUID.randomUUID();
    String endingTime = new Date().toString();
    item = new ItemEntity(id, "name",
            "description", endingTime, new BigDecimal("2.2"),
            1, "category", List.of("image"), user);
  }

  @Test
  public void testSearchForItem() {
    PaginationInput paginationInput = new PaginationInput();
    when(itemService.searchForItemsByName("name", paginationInput))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination()));

    SearchedItemsResponse result = itemQueries.searchForItems("name",
            paginationInput);

    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
  }

  @Test
  public void testSearchForItemsNullInputs() {
    when(itemService.searchForItemsByName(eq("name"), any(PaginationInput.class)))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination(0, 10, 1)));

    SearchedItemsResponse result = itemQueries.searchForItems("name", null);

    ArgumentCaptor<PaginationInput> paginationCaptor = ArgumentCaptor.forClass(PaginationInput.class);
    verify(itemService).searchForItemsByName(eq("name"), paginationCaptor.capture());

    PaginationInput capturedPagination = paginationCaptor.getValue();
    assert capturedPagination.getPage() == 0;
    assert capturedPagination.getSize() == 10;
    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
    assert result.getPagination().getPage() == 0;
    assert result.getPagination().getSize() == 10;
  }

  @Test
  public void testSearchForItemsPaginationSizeZero() {
    when(itemService.searchForItemsByName(eq("name"), any(PaginationInput.class)))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination(0, 10, 1)));

    SearchedItemsResponse result = itemQueries.searchForItems("name", new PaginationInput(0, 0));

    ArgumentCaptor<PaginationInput> paginationCaptor = ArgumentCaptor.forClass(PaginationInput.class);
    verify(itemService).searchForItemsByName(eq("name"), paginationCaptor.capture());

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
    when(itemService.searchForItemsByName(eq("name"), any(PaginationInput.class)))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination(0, 12, 1)));

    SearchedItemsResponse result = itemQueries.searchForItems("name", new PaginationInput(0, 12));

    ArgumentCaptor<PaginationInput> paginationCaptor = ArgumentCaptor.forClass(PaginationInput.class);
    verify(itemService).searchForItemsByName(eq("name"), paginationCaptor.capture());

    PaginationInput capturedPagination = paginationCaptor.getValue();
    assert capturedPagination.getPage() == 0;
    assert capturedPagination.getSize() == 12;
    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
    assert result.getPagination().getPage() == 0;
    assert result.getPagination().getSize() == 12;
  }

  @Test
  public void testGetItemById() {

    when(itemService.getItemById(item.getId())).thenReturn(item);

    ItemEntity result = itemQueries.getItemById(item.getId().toString());

    assert result.getId().equals(item.getId());
  }

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
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination()));

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
