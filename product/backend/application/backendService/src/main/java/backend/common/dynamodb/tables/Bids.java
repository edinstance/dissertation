package backend.common.dynamodb.tables;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

/**
 * Represents a bid entry for DynamoDB persistence.
 * It uses the AWS SDK v2 Enhanced Client.
 */
@DynamoDbBean
@Getter
@Setter
public class Bids {

  /**
   * The id of the bid.
   */
  private UUID bidId;

  /**
   * The id of the user the bid is for.
   */
  private UUID userId;

  /**
   * The id of the item the bid is for.
   */
  private UUID itemId;

  /**
   * The amount of the bid.
   */
  private BigDecimal amount;

  /**
   * When the bid was created.
   */
  private String createdAt;

  /**
   * The time to live for dynamodb deletion.
   */
  private Long ttlTimestamp;

  /**
   * Gets the bid ID.
   *
   * @return the bid ID.
   */
  @DynamoDbPartitionKey
  @DynamoDbAttribute("bidId")
  public UUID getBidId() {
    return bidId;
  }

  /**
   * Gets the item ID.
   *
   * @return the itemID.
   */
  @DynamoDbSecondaryPartitionKey(indexNames = "itemId-index")
  @DynamoDbAttribute("itemId")
  public UUID getItemId() {
    return itemId;
  }

  /**
   * Gets the createdAt of the bid.
   *
   * @return the createdAt.
   */
  @DynamoDbSortKey
  @DynamoDbAttribute("createdAt")
  public String getCreatedAt() {
    return createdAt;
  }

  /**
   * Gets the amount of the bid.
   *
   * @return the amount.
   */
  @DynamoDbAttribute("amount")
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * Gets the user ID.
   *
   * @return the UserId.
   */
  @DynamoDbAttribute("userId")
  public UUID getUserId() {
    return userId;
  }

  /**
   * Gets the TTL timestamp for the log entry.
   *
   * @return the TTL timestamp.
   */
  @DynamoDbAttribute("ttlTimestamp")
  public Long getTtlTimestamp() {
    return ttlTimestamp;
  }

  /**
   * Default constructor for Bids.  Required by DynamoDB Enhanced Client.
   */
  public Bids() {
  }

  /**
   * Constructor for Bids.
   *
   * @param bidId  the id of the bid.
   * @param userId the id of the user the bid is for.
   * @param itemId the id of the item the bid is for.
   * @param amount the amount of the bid.
   */
  public Bids(UUID bidId, UUID userId, UUID itemId, BigDecimal amount) {
    this.bidId = bidId;
    this.userId = userId;
    this.itemId = itemId;
    this.amount = amount;
  }
}
