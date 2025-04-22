package backend.BidTests.MapperTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import backend.bids.dto.CreateBidDto;
import backend.bids.mappers.MapCreateBidDtoToDynamo;
import backend.common.dynamodb.tables.Bids;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class MapCreateBidDtoToDynamoTests {

  @Test
  public void testBidsDtoNull() {
    assertNull(MapCreateBidDtoToDynamo.mapBidDtoToDynamo(null));
  }

  @Test
  public void testMapCreateBidDtoToDynamo() {
    UUID userId = UUID.randomUUID();
    UUID itemId = UUID.randomUUID();
    BigDecimal amount = new BigDecimal("4.5");
    String paymentMethod = "card";

    CreateBidDto createBidDto = new CreateBidDto(userId, itemId, amount, paymentMethod);

    Bids bid = MapCreateBidDtoToDynamo.mapBidDtoToDynamo(createBidDto);

    assertNotNull(bid);
    assert bid.getClass() == Bids.class;

    assert bid.getUserId().equals(userId);
    assert bid.getItemId().equals(itemId);
    assert bid.getAmount().equals(amount);
    assert bid.getPaymentMethod().equals(paymentMethod);
  }
}
