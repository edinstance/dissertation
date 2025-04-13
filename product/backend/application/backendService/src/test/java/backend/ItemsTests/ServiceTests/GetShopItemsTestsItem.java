package backend.ItemsTests.ServiceTests;

import backend.common.dto.PaginationInput;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetShopItemsTestsItem extends SetupItemServiceTests {

  @Test
  public void testGetShopItems() {
    when(itemRepository.getShopItems(sortInput.getSortBy(), sortInput.getSortDirection().name(), 0, 10)).thenReturn(List.of(item));
    when(itemRepository.getShopItemsPages(10)).thenReturn(2);

    itemService.getShopItems(new PaginationInput(0, 10), sortInput);

    verify(itemRepository, times(1)).getShopItems(sortInput.getSortBy(), sortInput.getSortDirection().name(), 0, 10);
  }
}
