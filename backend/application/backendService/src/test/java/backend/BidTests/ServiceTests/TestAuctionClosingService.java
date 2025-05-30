package backend.BidTests.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.bids.services.AuctionClosingService;
import backend.bids.services.RedisLeaderElectionService;
import backend.items.entities.ItemEntity;
import backend.items.repositories.ItemRepository;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
class TestAuctionClosingService {

  @Mock
  private ItemRepository itemRepository;

  @Mock
  private KafkaTemplate<String, ItemEntity> kafkaTemplate;

  @Mock
  private RedisLeaderElectionService leaderElectionService;

  @Mock
  private ApplicationContext applicationContext;

  @Spy
  @InjectMocks
  private AuctionClosingService auctionClosingService;

  @Test
  void testLeaderClosesAuctions() {
    when(leaderElectionService.isLeader()).thenReturn(true);
    when(applicationContext.getBean(AuctionClosingService.class)).thenReturn(auctionClosingService);

    auctionClosingService.scheduledAuctionClosing();

    verify(applicationContext, times(1)).getBean(AuctionClosingService.class);
    verify(auctionClosingService, times(1)).closeExpiredAuctions();
  }

  @Test
  void testNotLeaderDoesNotCloseAuctions() {
    when(leaderElectionService.isLeader()).thenReturn(false);

    auctionClosingService.scheduledAuctionClosing();

    verify(applicationContext, never()).getBean(AuctionClosingService.class);
    verify(auctionClosingService, never()).closeExpiredAuctions();
  }

  @Test
  void testItemsAreSentToKafka() {
    List<ItemEntity> expiredItems = createTestItems(3);
    when(itemRepository.getItemsFromFinishedAuctions()).thenReturn(expiredItems);
    when(kafkaTemplate.send(anyString(), anyString(), any(ItemEntity.class)))
            .thenReturn(null);

    auctionClosingService.closeExpiredAuctions();

    verify(itemRepository, times(1)).getItemsFromFinishedAuctions();
    for (ItemEntity item : expiredItems) {
      verify(kafkaTemplate, times(1)).send(
              eq("ClosedAuctions"),
              eq(item.getId().toString()),
              eq(item)
      );
    }
  }

  @Test
  void testNoItems() {
    when(itemRepository.getItemsFromFinishedAuctions()).thenReturn(new ArrayList<>());

    auctionClosingService.closeExpiredAuctions();

    verify(itemRepository, times(1)).getItemsFromFinishedAuctions();
    verify(kafkaTemplate, never()).send(anyString(), anyString(), any(ItemEntity.class));
  }

  @Test
  void testKafkaError() {
    List<ItemEntity> expiredItems = createTestItems(1);
    when(itemRepository.getItemsFromFinishedAuctions()).thenReturn(expiredItems);
    when(kafkaTemplate.send(anyString(), anyString(), any(ItemEntity.class)))
            .thenThrow(new RuntimeException("Kafka connection error"));

    Exception exception = assertThrows(RuntimeException.class, () -> auctionClosingService.closeExpiredAuctions());

    verify(itemRepository, times(1)).getItemsFromFinishedAuctions();
    verify(kafkaTemplate, times(1)).send(anyString(), anyString(), any(ItemEntity.class));

    assertEquals("Failed to send event to Kafka", exception.getMessage());
  }

  private List<ItemEntity> createTestItems(int count) {
    List<ItemEntity> items = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      ItemEntity item = new ItemEntity();
      item.setId(UUID.randomUUID());
      item.setName("Test Item " + i);
      item.setEndingTime(String.valueOf(LocalDateTime.now(ZoneOffset.UTC).minusHours(1)));
      items.add(item);
    }
    return items;
  }
}