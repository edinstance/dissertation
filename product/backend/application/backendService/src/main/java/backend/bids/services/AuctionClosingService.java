package backend.bids.services;

import static backend.common.config.kafka.KafkaTopics.CLOSED_AUCTION_TOPIC;

import backend.common.config.logging.AppLogger;
import backend.items.entities.ItemEntity;
import backend.items.repositories.ItemRepository;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Function to handle closing the auctions.
 */
@Service
@Profile("!test")
public class AuctionClosingService {

  /**
   * Date formatter.
   */
  private static final DateTimeFormatter FORMATTER =
          DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  /**
   * The item repository to use.
   */
  private final ItemRepository itemRepository;

  /**
   * The kafka template to use.
   */
  private final KafkaTemplate<String, Object> kafkaTemplate;

  /**
   * The redis leader election service to use.
   */
  private final RedisLeaderElectionService leaderElectionService;

  /**
   * The application context.
   */
  private final ApplicationContext applicationContext;

  /**
   * Constructor.
   *
   * @param itemRepository        the item repository to use.
   * @param kafkaTemplate         the kafka template to use.
   * @param leaderElectionService the leader election service to use.
   * @param applicationContext    the application context.
   */
  @Autowired
  public AuctionClosingService(
          ItemRepository itemRepository,
          KafkaTemplate<String, Object> kafkaTemplate,
          RedisLeaderElectionService leaderElectionService,
          ApplicationContext applicationContext) {
    this.itemRepository = itemRepository;
    this.kafkaTemplate = kafkaTemplate;
    this.leaderElectionService = leaderElectionService;
    this.applicationContext = applicationContext;
  }

  /**
   * Function to schedule closing auctions.
   */
  @Scheduled(cron = "1 * * * * *")
  public void scheduledAuctionClosing() {
    LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
    String timestamp = now.format(FORMATTER);
    AppLogger.info("AuctionClosingService scheduled at " + timestamp);

    // Only run if we're the leader
    if (leaderElectionService.isLeader()) {
      AppLogger.info("Leader instance starting scheduled auction closing at {}", timestamp);

      AuctionClosingService proxy = applicationContext.getBean(AuctionClosingService.class);
      proxy.closeExpiredAuctions();
    } else {
      AppLogger.info("Instance is not leader, skipping scheduled auction closing at {}", timestamp);
    }
  }

  /**
   * Function to close the expired auctions.
   */
  public void closeExpiredAuctions() {
    LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
    String formattedTimestamp = now.format(FORMATTER);

    AppLogger.info("Looking for expired auctions to close at {}", formattedTimestamp);

    // Find expired auctions with lock using repository
    List<ItemEntity> expiredAuctions = itemRepository.getItemsFromFinishedAuctions();

    if (expiredAuctions.isEmpty()) {
      AppLogger.info("No expired auctions found at {}", formattedTimestamp);
      return;
    }

    AppLogger.info("Closing {} expired auctions at {}", expiredAuctions.size(), formattedTimestamp);

    for (ItemEntity item : expiredAuctions) {
      try {

        kafkaTemplate.send(CLOSED_AUCTION_TOPIC, item.getId().toString(), item);

        AppLogger.info("Sent close event for item {} to Kafka at {}",
                item.getId(), formattedTimestamp);

      } catch (Exception e) {
        AppLogger.error("Failed to send close event for item {} to Kafka at {}: {}",
                item.getId(), formattedTimestamp, e.getMessage(), e);
        throw new RuntimeException("Failed to send event to Kafka", e);
      }
    }
  }
}