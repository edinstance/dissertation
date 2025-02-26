package com.finalproject.backend.ItemsTests.QueryTests;

import com.finalproject.backend.common.dto.PaginationInput;
import com.finalproject.backend.items.dto.SearchedItemsResponse;
import com.finalproject.backend.items.entities.ItemEntity;
import com.finalproject.backend.users.entities.UserEntity;
import com.finalproject.backend.common.helpers.Pagination;
import com.finalproject.backend.items.queries.ItemQueries;
import com.finalproject.backend.items.services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

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
    when(itemService.searchForItemsByName("name", paginationInput ))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination()));

    SearchedItemsResponse result = itemQueries.searchForItems("name",
            paginationInput);

    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
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
    when(itemService.getItemsByUser(id, true, paginationInput ))
            .thenReturn(new SearchedItemsResponse(List.of(item), new Pagination()));

    SearchedItemsResponse result = itemQueries.getItemsByUser(id.toString(), true,
            paginationInput);

    assert result.getItems().size() == 1;
    assert result.getItems().contains(item);
  }
}
