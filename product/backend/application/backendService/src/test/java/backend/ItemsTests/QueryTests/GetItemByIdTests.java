package backend.ItemsTests.QueryTests;

import static org.mockito.Mockito.when;

import backend.items.entities.ItemEntity;
import org.junit.jupiter.api.Test;

public class GetItemByIdTests extends SetupQueryTests {

  @Test
  public void testGetItemById() {

    when(itemService.getItemById(item.getId())).thenReturn(item);

    ItemEntity result = itemQueries.getItemById(item.getId().toString());

    assert result.getId().equals(item.getId());
  }
}
