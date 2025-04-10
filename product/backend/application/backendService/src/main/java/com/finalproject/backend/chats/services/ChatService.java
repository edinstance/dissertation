package com.finalproject.backend.chats.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.chats.dynamodb.ChatsDynamoService;
import com.finalproject.backend.chats.streams.ChatStream;
import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

    public void createChat(String message) {
        Instant now = Instant.now();
        UUID chatId = UUID.randomUUID();
        UUID userId = authHelpers.getCurrentUserId();

        Chat chat = new Chat(
                chatId,
                userId,
                now.toString(),
                "User " + userId,
                message
        );

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.zadd("chat:" + userId, now.toEpochMilli(), objectMapper.writeValueAsString(chat));
            jedis.expire("chat:" + userId, 600L);
        } catch (Exception e) {
            AppLogger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        chatsDynamoService.writeChat(chat);

        Chat response = createResponse(chatId, chat.getUserId());
        chatStream.publish(response);
    }

    public Chat createResponse(final UUID chatId, final UUID userId) {
        Instant now = Instant.now();
        return new Chat(chatId, userId, now.toString(),
                "System", "Message placeholder");
    }

    public List<Chat> getCurrentMessages() {
        List<Chat> messages = new ArrayList<>();
        try (Jedis jedis = jedisPool.getResource()) {
            String chatKey = "chat:" + authHelpers.getCurrentUserId();

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
