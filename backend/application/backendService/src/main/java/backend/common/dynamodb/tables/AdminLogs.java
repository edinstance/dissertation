package backend.common.dynamodb.tables;

import java.util.UUID;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

/**
 * Represents an Admin Log entry for DynamoDB persistence
 * using the AWS SDK v2 Enhanced Client.
 */
@DynamoDbBean
@Setter
public class AdminLogs {

  private UUID adminId;
  private String timestamp;

  private UUID logId;
  private Boolean success;
  private String queryType;
  private String queryName;
  private String ipAddress;
  private String userAgent;
  private Long ttlTimestamp;


  /**
   * Gets the admin ID.
   *
   * @return the admin ID.
   */
  @DynamoDbPartitionKey
  @DynamoDbAttribute("adminId")
  public UUID getAdminId() {
    return adminId;
  }

  /**
   * Gets the timestamp of the log entry.
   *
   * @return the timestamp.
   */
  @DynamoDbSortKey
  @DynamoDbAttribute("timestamp")
  public String getTimestamp() {
    return timestamp;
  }

  /**
   * Gets the log ID.
   *
   * @return the log ID.
   */
  @DynamoDbAttribute("logId")
  public UUID getLogId() {
    return logId;
  }

  /**
   * The success status of the query performed by the admin.
   *
   * @return the success status.
   */
  @DynamoDbAttribute("success")
  public Boolean getSuccess() {
    return success;
  }

  /**
   * Gets the queryType performed by the admin.
   *
   * @return the queryType.
   */
  @DynamoDbAttribute("queryType")
  public String getQueryType() {
    return queryType;
  }

  /**
   * Gets the queryName affected by the queryType.
   *
   * @return the queryName.
   */
  @DynamoDbAttribute("queryName")
  public String getQueryName() {
    return queryName;
  }

  /**
   * Gets the IP address of the admin.
   *
   * @return the IP address.
   */
  @DynamoDbAttribute("ipAddress")
  public String getIpAddress() {
    return ipAddress;
  }

  /**
   * Gets the user agent of the admin.
   *
   * @return the user agent.
   */
  @DynamoDbAttribute("userAgent")
  public String getUserAgent() {
    return userAgent;
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
   * Default constructor for AdminLogs.
   */
  public AdminLogs() {

  }

  /**
   * Constructor for AdminLogs.
   *
   * @param adminId   the admin ID.
   * @param logId     the log ID.
   * @param queryType the queryType performed by the admin.
   * @param queryName the queryName affected by the queryType.
   */
  public AdminLogs(UUID adminId, UUID logId,
                   String queryType, String queryName) {
    this.adminId = adminId;
    this.logId = logId;
    this.queryType = queryType;
    this.queryName = queryName;
  }
}
