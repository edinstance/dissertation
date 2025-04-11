package com.finalproject.backend.chats.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.chats.dynamodb.ChatsDynamoService;
import com.finalproject.backend.chats.streams.ChatStream;
import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import com.finalproject.backend.common.helpers.AuthHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ChatService {

    private final ChatsDynamoService chatsDynamoService;
    private final ChatStream chatStream;
    private final AuthHelpers authHelpers;
    private final JedisPool jedisPool;
    private final ObjectMapper objectMapper;

    @Autowired
    public ChatService(ChatsDynamoService chatsDynamoService, ChatStream chatStream, AuthHelpers authHelpers, JedisPool jedisPool, ObjectMapper objectMapper) {
        this.chatsDynamoService = chatsDynamoService;
        this.chatStream = chatStream;
        this.authHelpers = authHelpers;
        this.jedisPool = jedisPool;
        this.objectMapper = objectMapper;
    }

    public void createChat(UUID conversationId, String message) {
        Instant now = Instant.now();
        UUID chatId = UUID.randomUUID();
        UUID userId = authHelpers.getCurrentUserId();

        Chat chat = new Chat(
                conversationId,
                chatId,
                userId,
                now.toString(),
                "User " + userId,
                message
        );

        addChatToCache(conversationId, now, chat);

        chatsDynamoService.writeChat(chat);
        chatStream.publish(chat);

        createResponse(conversationId);
    }

    public void createResponse(final UUID conversationId) {
        Instant now = Instant.now();


        Chat chat = new Chat(
                conversationId,
                UUID.randomUUID(),
                null,
                now.toString(),
                "System",
                "Message placeholder"
        );

        addChatToCache(conversationId, now, chat);

        AppLogger.info("System About to publish chat with conversationId: " + chat.getConversationId() + " message: " + chat.getMessage());

        chatStream.publish(chat);
    }

    private void addChatToCache(UUID conversationId, Instant now, Chat chat) {
        try (Jedis jedis = jedisPool.getResource()) {

            jedis.zadd("chat:" + conversationId, now.toEpochMilli(), objectMapper.writeValueAsString(chat));

            jedis.expire("chat:" + conversationId, 600L);
        } catch (Exception e) {
            AppLogger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void clearCurrentConversation(UUID conversationId) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del("chat:" + conversationId);
        } catch (Exception e) {
            AppLogger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<Chat> getCurrentMessages(UUID conversationId) {
        List<Chat> messages = new ArrayList<>();
        try (Jedis jedis = jedisPool.getResource()) {
            String chatKey = "chat:" + conversationId;

            List<String> messageJsonSet = jedis.zrange(chatKey, 0, -1);

            for (String messageJson : messageJsonSet) {
                try {
                    Chat chat = objectMapper.readValue(messageJson, Chat.class);
                    messages.add(chat);
                } catch (Exception e) {
                    AppLogger.error(e.getMessage());
                    return new ArrayList<>();
                }
            }

            return messages;

        } catch (Exception e) {
            AppLogger.error(e.getMessage());
            return new ArrayList<>();
        }
    }
}
