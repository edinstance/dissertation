package backend.bids.dynamodb;

import backend.bids.dto.CreateBidDto;
import backend.bids.mappers.MapCreateBidDtoToDynamo;
import backend.common.config.logging.AppLogger;
import backend.common.dynamodb.tables.Bids;
import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

/**
 * This service handles the interaction with DynamoDB for bids.
 */
@Service
public class BidsDynamoService {

  /**
   * The DynamoDB enhanced client for interacting with DynamoDB.
   */
  private final DynamoDbEnhancedClient enhancedClient;

  /**
   * The DynamoDB table for bids.
   */
  private DynamoDbTable<Bids> bidsTable;

  /**
   * The item id index.
   */
  private DynamoDbIndex<Bids> itemIdIndex;

  /**
   * The number of days to retain logs in DynamoDB.
   */
  private static final long LOG_RETENTION_DAYS = 720;

  /**
   * The name of the item id index.
   */
  private static final String ITEM_ID_INDEX_NAME = "itemId-index";

  /**
   * The environment in which the application is running.
   */
  @Value("${spring.profiles.active:local}")
  private String environment;

  /**
   * Constructs an BidsDynamoService with the specified DynamoDbEnhancedClient.
   *
   * @param enhancedClient The DynamoDB enhanced client for interacting with DynamoDB.
   */
  @Autowired
  public BidsDynamoService(DynamoDbEnhancedClient enhancedClient) {
    this.enhancedClient = enhancedClient;
  }

  /**
   * Initializes the DynamoDB table for bids.
   */
  @PostConstruct
  public void initializeTable() {
    String tableName = String.format("%s-bids", this.environment);
    try {
      this.bidsTable = enhancedClient.table(tableName, TableSchema.fromBean(Bids.class));
      this.itemIdIndex = this.bidsTable.index(ITEM_ID_INDEX_NAME);
      AppLogger.info("Initialized DynamoDB table for: {}", tableName);
    } catch (Exception e) {
      AppLogger.error("Failed to initialize DynamoDB table", e);
    }
  }

  /**
   * Writes a new bid to DynamoDB.
   * Automatically sets timestamp, bidId, and ttlTimestamp.
   **/
  public void writeBid(CreateBidDto bidDto) {
    if (bidsTable == null) {
      AppLogger.error("Cannot write log. DynamoDB table reference is not initialized.");
      return;
    }
    if (bidDto == null || bidDto.getBidId() == null) {
      AppLogger.error("Cannot write log. Log entry or bidId is null.");
      return;
    }

    try {
      Instant now = Instant.now();
      long ttlEpochSeconds = now.plus(LOG_RETENTION_DAYS, ChronoUnit.DAYS).getEpochSecond();

      Bids bids = MapCreateBidDtoToDynamo.mapBidDtoToDynamo(bidDto);

      bids.setCreatedAt(now.toString());
      bids.setTtlTimestamp(ttlEpochSeconds);

      PutItemEnhancedRequest<Bids> request = PutItemEnhancedRequest.builder(Bids.class)
              .item(bids)
              .build();

      bidsTable.putItem(request);
      AppLogger.info("Bid written successfully: {}", bids.getBidId());

    } catch (DynamoDbException e) {
      AppLogger.error("Error writing bid to DynamoDB", e);
    } catch (Exception e) {
      AppLogger.error("Unexpected error writing bid", e);
    }
  }

  /**
   * Retrieves the most recent bid for an item from DynamoDB.
   *
   * @param itemId The ID of the item for which to retrieve the most recent bid.
   *
   * @return The most recent bid, or null if no bids exist for the item.
   */
  public Bids getMostRecentBid(UUID itemId) {
    if (bidsTable == null) {
      AppLogger.error("Cannot retrieve bid. DynamoDB table reference is not initialized.");
      return null;
    }

    try {

      Bids firstItem = getItemBids(itemId).getFirst();

      if (firstItem != null) {
        return firstItem;
      } else {
        AppLogger.info("No bids found");
        return null;
      }

    } catch (DynamoDbException e) {
      AppLogger.error("Error retrieving most recent bid for item : {}", e);
      return null;
    } catch (Exception e) {
      AppLogger.error("Unexpected error retrieving most recent bid for item: {}", e);
      return null;
    }
  }


  /**
   * This function gets the item bid history based on the itemId.
   *
   * @param itemId the item to search by.
   *
   * @return a list of the items bids.
   */
  public List<Bids> getItemBids(UUID itemId) {
    if (bidsTable == null) {
      AppLogger.error("Cannot retrieve item bids. DynamoDB table reference is not initialized.");
      return List.of();
    }
    if (itemId == null) {
      AppLogger.warn("Cannot retrieve item bids. Item ID is null.");
      return List.of();
    }

    try {
      QueryEnhancedRequest request = QueryEnhancedRequest.builder()
              .queryConditional(
                      QueryConditional.keyEqualTo(
                              k -> k.partitionValue(itemId.toString())))
              .scanIndexForward(false)
              .build();

      PageIterable<Bids> pages = (PageIterable<Bids>) itemIdIndex.query(request);

      List<Bids> bids = pages.stream()
              .flatMap(page -> page.items().stream())
              .toList();

      if (bids.isEmpty()) {
        AppLogger.info("No bids found for item {}", itemId);
      } else {
        AppLogger.info("Retrieved {} bids for item {}", bids.size(), itemId);
      }
      return bids;

    } catch (DynamoDbException e) {
      AppLogger.error("DynamoDB error retrieving bids for item {}: {}",
              itemId, e.awsErrorDetails().errorMessage(), e);
      return List.of();
    } catch (Exception e) {
      AppLogger.error("Unexpected error retrieving bids for item {}: {}",
              itemId, e.getMessage(), e);
      return List.of();
    }
  }

}
