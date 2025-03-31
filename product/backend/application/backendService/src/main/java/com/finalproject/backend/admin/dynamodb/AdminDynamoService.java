package com.finalproject.backend.admin.dynamodb;

import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.common.dynamodb.tables.AdminLogs;
import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

/**
 * This service handles the interaction with DynamoDB for admin access logs.
 */
@Service
public class AdminDynamoService {

  /**
   * The DynamoDB enhanced client for interacting with DynamoDB.
   */
  private final DynamoDbEnhancedClient enhancedClient;

  /**
   * The DynamoDB table for admin access logs.
   */
  private DynamoDbTable<AdminLogs> logTable;

  /**
   * The number of days to retain logs in DynamoDB.
   */
  private static final long LOG_RETENTION_DAYS = 90;

  /**
   * The environment in which the application is running.
   */
  @Value("${spring.profiles.active:local}")
  private String environment;

  /**
   * Constructs an AdminDynamoService with the specified DynamoDbEnhancedClient.
   *
   * @param enhancedClient The DynamoDB enhanced client for interacting with DynamoDB.
   */
  @Autowired
  public AdminDynamoService(DynamoDbEnhancedClient enhancedClient) {
    this.enhancedClient = enhancedClient;
  }

  /**
   * Initializes the DynamoDB table for admin access logs.
   */
  @PostConstruct
  public void initializeTable() {
    String tableName = String.format("%s-admin-access-logs", this.environment);
    try {
      this.logTable = enhancedClient.table(tableName, TableSchema.fromBean(AdminLogs.class));
      AppLogger.info("Initialized DynamoDB table for: {}", tableName);
    } catch (Exception e) {
      AppLogger.error("Failed to initialize DynamoDB table", e);
    }
  }

  /**
   * Writes a new admin log entry to DynamoDB.
   * Automatically sets timestamp, logId, and ttlTimestamp.
   *
   * @param logEntry A partially populated AdminLogs object
   *                 (adminId, queryType, status etc. should be set by caller).
   */
  public void writeAdminLog(AdminLogs logEntry) {
    if (logTable == null) {
      AppLogger.error("Cannot write log. DynamoDB table reference is not initialized.");
      return;
    }
    if (logEntry == null || logEntry.getAdminId() == null) {
      AppLogger.error("Cannot write log. Log entry or adminId is null.");
      return;
    }

    try {
      Instant now = Instant.now();
      long ttlEpochSeconds = now.plus(LOG_RETENTION_DAYS, ChronoUnit.DAYS).getEpochSecond();


      logEntry.setTimestamp(now.toString());

      if (logEntry.getLogId() == null) {
        logEntry.setLogId(UUID.randomUUID());
      }
      logEntry.setTtlTimestamp(ttlEpochSeconds);

      PutItemEnhancedRequest<AdminLogs> request = PutItemEnhancedRequest.builder(AdminLogs.class)
              .item(logEntry)
              .build();

      logTable.putItem(request);
      AppLogger.info("Admin log written successfully: {}", logEntry.getLogId());

    } catch (DynamoDbException e) {
      AppLogger.error("Error writing admin log to DynamoDB for admin", e);
    } catch (Exception e) {
      AppLogger.error("Unexpected error writing admin log", e);
    }
  }


}
