package com.finalproject.backend.chats.dynamodb;

import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
public class ChatsDynamoService {

    /**
     * The DynamoDB enhanced client for interacting with DynamoDB.
     */
    private final DynamoDbEnhancedClient enhancedClient;

    /**
     * The DynamoDB table for chats.
     */
    private DynamoDbTable<Chat> chatTable;

    /**
     * The number of days to retain chats in DynamoDB.
     */
    private static final long CHAT_RETENTION_DAYS = 30;

    /**
     * The environment in which the application is running.
     */
    @Value("${spring.profiles.active:local}")
    private String environment;

    /**
     * Constructs an ChatDynamoService with the specified DynamoDbEnhancedClient.
     *
     * @param enhancedClient The DynamoDB enhanced client for interacting with DynamoDB.
     */
    @Autowired
    public ChatsDynamoService(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    /**
     * Initializes the DynamoDB table for chats.
     */
    @PostConstruct
    public void initializeTable() {
        String tableName = String.format("%s-chats", this.environment);
        try {
            this.chatTable = enhancedClient.table(tableName, TableSchema.fromBean(Chat.class));
            AppLogger.info("Initialized DynamoDB table for: {}", tableName);
        } catch (Exception e) {
            AppLogger.error("Failed to initialize DynamoDB table", e);
        }
    }

    /**
     * Writes a new chat to DynamoDB.
     * Automatically sets timestamp, chatId, and ttlTimestamp.
     *
     * @param chat A partially populated Chat object
     */
    public void writeChat(Chat chat) {
        if (chatTable == null) {
            AppLogger.error("Cannot write log. DynamoDB table reference is not initialized.");
            return;
        }
        if (chat == null || chat.getUserId() == null) {
            AppLogger.error("Cannot write log. Chat or userId are null.");
            return;
        }

        try {
            Instant now = Instant.now();
            long ttlEpochSeconds = now.plus(CHAT_RETENTION_DAYS, ChronoUnit.DAYS).getEpochSecond();

            chat.setTtlTimestamp(ttlEpochSeconds);

            PutItemEnhancedRequest<Chat> request = PutItemEnhancedRequest.builder(Chat.class)
                    .item(chat)
                    .build();

            chatTable.putItem(request);
            AppLogger.info("Chat written successfully: {}", chat.getChatId());

        } catch (DynamoDbException e) {
            AppLogger.error("Error writing chat to DynamoDB for sender", e);
        } catch (Exception e) {
            AppLogger.error("Unexpected error writing chat", e);
        }
    }

}
