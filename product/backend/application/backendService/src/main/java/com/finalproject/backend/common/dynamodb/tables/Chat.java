package com.finalproject.backend.common.dynamodb.tables;

import java.util.UUID;

import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

/**
 * Represents a Chat message entry for DynamoDB persistence
 * using the AWS SDK v2 Enhanced Client.
 */
@DynamoDbBean
@Setter
public class Chat {

    /**
     * The id of a conversation
     */
    private UUID conversationId;

    /**
     * The id of a chat.
     */
    private UUID chatId;

    /**
     * The id of the user the chats are for.
     */
    private UUID userId;

    /**
     * When the chat was created.
     */
    private String createdAt;

    /**
     * Who sent the chat.
     */
    private String sender;

    /**
     * The message of the chat.
     */
    private String message;

    /**
     * The time to live for dynamodb deletion.
     */
    private Long ttlTimestamp;


    /**
     * Gets the conversation ID.
     *
     * @return the conversation ID.
     */
    @DynamoDbPartitionKey
    @DynamoDbAttribute("conversationId")
    public UUID getConversationId() {
        return conversationId;
    }

    /**
     * Gets the chat ID.
     *
     * @return the chat ID.
     */
    @DynamoDbSecondaryPartitionKey(indexNames = "chatId")
    @DynamoDbAttribute("chatId")
    public UUID getChatId() {
        return chatId;
    }

    /**
     * Gets the user ID.
     *
     * @return the UserId.
     */
    @DynamoDbSecondaryPartitionKey(indexNames = "userId")
    @DynamoDbAttribute("userId")
    public UUID getUserId() {
        return userId;
    }

    /**
     * Gets the createdAt of the chat.
     *
     * @return the createdAt.
     */
    @DynamoDbSortKey
    @DynamoDbAttribute("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the sender of the chat.
     *
     * @return the sender.
     */
    @DynamoDbAttribute("sender")
    public String getSender() {
        return sender;
    }

    /**
     * Gets the content of the chat message.
     *
     * @return the message content.
     */
    @DynamoDbAttribute("message")
    public String getMessage() {
        return message;
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
     * Default constructor for Chat.  Required by DynamoDB Enhanced Client.
     */
    public Chat() {
    }

    /**
     * Constructor for Chat.
     *
     * @param conversationId the id of the conversation.
     * @param chatId         the chat ID.
     * @param userId         the id of the user the chat is for.
     * @param createdAt      when the chat was created.
     * @param sender         the sender of the message.
     * @param message        the message content.
     */
    public Chat(UUID conversationId, UUID chatId, UUID userId,
                String createdAt, String sender, String message) {
        this.conversationId = conversationId;
        this.chatId = chatId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.sender = sender;
        this.message = message;
    }
}
