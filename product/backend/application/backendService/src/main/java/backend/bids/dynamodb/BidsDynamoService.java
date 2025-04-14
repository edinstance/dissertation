package backend.bids.dynamodb;

import backend.bids.dto.CreateBidDto;
import backend.bids.mappers.MapCreateBidDtoToDynamo;
import backend.common.config.logging.AppLogger;
import backend.common.dynamodb.tables.Bids;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

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
   * The number of days to retain logs in DynamoDB.
   */
  private static final long LOG_RETENTION_DAYS = 720;

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


}
