package backend.BidTests.MapperTests;

import backend.bids.dto.CreateBidDto;
import backend.bids.mappers.MapCreateBidDtoToDynamo;
import backend.common.dynamodb.tables.Bids;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MapCreateBidDtoToDynamoTests {

  @Test
  public void testBidsDtoNull(){
    assertNull(MapCreateBidDtoToDynamo.mapBidDtoToDynamo(null));
  }

  @Test
  public void testMapCreateBidDtoToDynamo() {
    UUID userId = UUID.randomUUID();
    UUID itemId = UUID.randomUUID();
    BigDecimal amount = new BigDecimal("4.5");

    CreateBidDto createBidDto = new CreateBidDto(userId, itemId, amount);

    Bids bid = MapCreateBidDtoToDynamo.mapBidDtoToDynamo(createBidDto);

    assertNotNull(bid);
    assert bid.getClass() == Bids.class;

    assert bid.getUserId().equals(userId);
    assert bid.getItemId().equals(itemId);
    assert bid.getAmount().equals(amount);
  }
}
