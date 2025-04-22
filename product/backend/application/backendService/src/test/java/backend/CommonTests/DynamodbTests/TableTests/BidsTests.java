package backend.CommonTests.DynamodbTests.TableTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import backend.common.dynamodb.tables.Bids;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BidsTests {

  private Bids bid;

  @Test
  public void testDefaultConstructor() {
    bid = new Bids();
    assertNotNull(bid);
  }

  @Test
  public void testConstructor() {
    bid = new Bids(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), new BigDecimal("4.5"), "card");

    assertNotNull(bid);
    assertNotNull(bid.getBidId());
    assertNotNull(bid.getUserId());
    assertNotNull(bid.getItemId());
  }

  @BeforeEach
  public void setUp() {
    bid = new Bids();
  }

  @Test
  public void testBidIdMethods() {
    UUID bidId = UUID.randomUUID();
    bid.setBidId(bidId);
    assertEquals(bidId, bid.getBidId());
  }

  @Test
  public void testTimestampMethods() {
    Instant now = Instant.now();
    String timestamp = now.toString();
    bid.setCreatedAt(timestamp);
    assertEquals(timestamp, bid.getCreatedAt());
  }

  @Test
  public void testUserIdMethods() {
    UUID userId = UUID.randomUUID();
    bid.setUserId(userId);
    assertEquals(userId, bid.getUserId());
  }

  @Test
  public void testItemIdMethods() {
    UUID itemId = UUID.randomUUID();
    bid.setItemId(itemId);
    assertEquals(itemId, bid.getItemId());
  }

  @Test
  public void testAmountMethods() {
    BigDecimal amount = new BigDecimal("5.65");
    bid.setAmount(amount);
    assertEquals(amount, bid.getAmount());
  }

  @Test
  public void testTTLTimestampMethods() {
    Instant now = Instant.now();
    long timestamp = now.toEpochMilli();
    bid.setTtlTimestamp(timestamp);
    assertEquals(timestamp, bid.getTtlTimestamp());
  }

}
