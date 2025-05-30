package backend.ItemsTests.ServiceTests;

import backend.common.dto.PaginationInput;
import backend.common.dto.SortInput;
import backend.common.helpers.AuthHelpers;
import backend.common.types.SortDirection;
import backend.items.entities.ItemEntity;
import backend.items.helpers.ItemCacheHelpers;
import backend.items.repositories.ItemRepository;
import backend.items.services.ItemService;
import backend.users.entities.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@ExtendWith(MockitoExtension.class)
public class SetupItemServiceTests {

  public final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public ObjectMapper objectMapper = new ObjectMapper();

  @Mock
  public ItemRepository itemRepository;

  @Mock
  public JedisPool jedisPool;

  @Mock
  public Jedis jedis;

  @Mock
  public AuthHelpers authHelpers;

  @Mock
  public ItemCacheHelpers itemCacheHelpers;

  @InjectMocks
  public ItemService itemService;

  public UUID itemId;
  public UserEntity user;
  public ItemEntity item;
  public PaginationInput paginationInput;
  public SortInput sortInput;

  @BeforeEach
  public void setUp() throws ParseException {
    itemId = UUID.randomUUID();
    user = new UserEntity(UUID.randomUUID(),
            "seller@test.com", "Seller Name");
    item = new ItemEntity(itemId, "Item Name",
            "Item Description", dateFormat.format(new Date()), new BigDecimal("19.99"), 100,
            "Category",
            List.of("image"), user);
    paginationInput = new PaginationInput();
    sortInput = new SortInput("name", SortDirection.ASC);
  }
}
