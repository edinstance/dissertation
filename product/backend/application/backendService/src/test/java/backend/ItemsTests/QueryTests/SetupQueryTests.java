package backend.ItemsTests.QueryTests;

import backend.items.entities.ItemEntity;
import backend.items.queries.ItemQueries;
import backend.items.services.ItemService;
import backend.users.entities.UserEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SetupQueryTests {

  public ItemEntity item;
  public UUID id;
  @Mock
  ItemService itemService;
  @InjectMocks
  ItemQueries itemQueries;

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

}