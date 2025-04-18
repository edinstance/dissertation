package backend.BidTests.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.bids.dto.CreateBidDto;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.SendResult;

@ExtendWith(MockitoExtension.class)
public class BidMutationServiceTests extends SetupBidMutationServiceTests {

  @Test
  void testNullBidDto() {
    assertFalse(bidMutationService.createBid(null));
  }

  @Test
  void testNullItemId() {
    CreateBidDto bidDto = new CreateBidDto(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.TEN);
    bidDto.setAmount(BigDecimal.TEN);
    assertFalse(bidMutationService.createBid(bidDto));
  }

  @Test
  void testNullAmount() {
    CreateBidDto bidDto = new CreateBidDto(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.TEN);
    bidDto.setItemId(UUID.randomUUID());
    assertFalse(bidMutationService.createBid(bidDto));
  }

  @Test
  void testBidLessThanCurrentHighestBid() {
    CreateBidDto bidDto = new CreateBidDto(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.TEN);
    UUID itemId = UUID.randomUUID();
    bidDto.setItemId(itemId);
    bidDto.setAmount(BigDecimal.ONE);

    when(bidHelpers.getCurrentHighestBid(itemId)).thenReturn(BigDecimal.TEN);

    assertFalse(bidMutationService.createBid(bidDto));
  }

  @Test
  void testGetCurrentHighestBidThrowsException() {
    CreateBidDto bidDto = new CreateBidDto(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.TEN);
    UUID itemId = UUID.randomUUID();
    bidDto.setItemId(itemId);
    bidDto.setAmount(BigDecimal.TEN);

    when(bidHelpers.getCurrentHighestBid(itemId)).thenThrow(new RuntimeException("DB Error"));

    assertFalse(bidMutationService.createBid(bidDto));
  }

  @Test
  void testSuccessfulBidCreation() {
    CreateBidDto bidDto = new CreateBidDto();
    UUID itemId = UUID.randomUUID();
    UUID bidId = UUID.randomUUID();
    bidDto.setItemId(itemId);
    bidDto.setBidId(bidId);
    bidDto.setAmount(BigDecimal.TEN);

    when(bidHelpers.getCurrentHighestBid(itemId)).thenReturn(BigDecimal.ONE);

    doAnswer(
            invocation -> {
              CompletableFuture<SendResult<String, Object>> future =
                      invocation.getArgument(6);
              future.complete(null);
              return null;
            })
            .when(bidKafkaHelpers)
            .attemptSend(
                    eq("Bids"),
                    eq(bidId.toString()),
                    any(),
                    eq(3),
                    eq(1000L),
                    eq(0),
                    any());

    boolean result = bidMutationService.createBid(bidDto);

    assertTrue(result);

    verify(bidKafkaHelpers, times(1))
            .attemptSend(
                    eq("Bids"),
                    eq(bidId.toString()),
                    any(),
                    eq(3),
                    eq(1000L),
                    eq(0),
                    any());
    verify(bidCacheHelpers, times(1)).updateCachedHighestBid(itemId, bidDto.getAmount());
  }


  @Test
  void testKafkaSendThrowsException() {
    CreateBidDto bidDto = new CreateBidDto(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.TEN);
    UUID itemId = UUID.randomUUID();
    bidDto.setItemId(itemId);
    bidDto.setAmount(BigDecimal.TEN);

    when(bidHelpers.getCurrentHighestBid(itemId)).thenReturn(BigDecimal.ONE);
    doThrow(new RuntimeException("Kafka Error"))
            .when(bidKafkaHelpers)
            .attemptSend(eq("Bids"), eq(bidDto.getBidId().toString()), eq(bidDto), eq(5), eq(100L), eq(0), any());

    assertFalse(bidMutationService.createBid(bidDto));
  }
}
