package backend.BidTests.DtoTests;

import backend.bids.dto.CreateBidDto;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateBidDtoTests {

  private CreateBidDto createBidDto;

  @Test
  public void testDefaultConstructor() {
    createBidDto = new CreateBidDto();

    assertNotNull(createBidDto);
  }

  @Test
  public void testConstructor() {
    createBidDto = new CreateBidDto(UUID.randomUUID(), UUID.randomUUID(), new BigDecimal("4.5"));

    assertNotNull(createBidDto);
  }

  @BeforeEach
  void setUp() {
    createBidDto = new CreateBidDto(UUID.randomUUID(), UUID.randomUUID(), new BigDecimal("4.5"));
  }

  @Test
  public void testBidIdMethods() {
    UUID bidId = UUID.randomUUID();
    createBidDto.setBidId(bidId);
    assertEquals(bidId, createBidDto.getBidId());
  }

  @Test
  public void testUserIdMethods() {
    UUID userId = UUID.randomUUID();
    createBidDto.setUserId(userId);
    assertEquals(userId, createBidDto.getUserId());
  }

  @Test
  public void testItemIdMethods() {
    UUID itemId = UUID.randomUUID();
    createBidDto.setItemId(itemId);
    assertEquals(itemId, createBidDto.getItemId());
  }

  @Test
  public void testAmountMethods() {
    BigDecimal amount = new BigDecimal("6.7");
    createBidDto.setAmount(amount);
    assertEquals(amount, createBidDto.getAmount());
  }
}
