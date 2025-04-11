package com.finalproject.backend.ChatTests.ServiceTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.chats.dynamodb.ChatsDynamoService;
import com.finalproject.backend.chats.services.ChatService;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import com.finalproject.backend.common.helpers.AuthHelpers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTests {

    @Mock
    private ChatsDynamoService chatsDynamoService;

    @Mock
    private AuthHelpers authHelpers;

    @Mock
    private JedisPool jedisPool;

    @Mock
    private Jedis jedis;

    @Mock
    private ObjectMapper mockObjectMapper;

    @InjectMocks
    private ChatService chatService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UUID conversationId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();

    @Test
    public void createChatTest() throws JsonProcessingException {
        ArgumentCaptor<Chat> dynamoChatCaptor = ArgumentCaptor.forClass(Chat.class);
        Chat chat = new Chat();

        String message = "Test Message";

        when(authHelpers.getCurrentUserId()).thenReturn(userId);
        when(jedisPool.getResource()).thenReturn(jedis);
        when(mockObjectMapper.writeValueAsString(any(Chat.class))).thenReturn(objectMapper.writeValueAsString(chat));
        doNothing().when(chatsDynamoService).writeChat(any(Chat.class));

        chatService.createChat(conversationId, message);

        verify(chatsDynamoService, times(1)).writeChat(dynamoChatCaptor.capture());

        Chat dynamoCapturedChat = dynamoChatCaptor.getValue();

        assertNotNull(dynamoCapturedChat);
        assertEquals(conversationId, dynamoCapturedChat.getConversationId());
        assertEquals(userId, dynamoCapturedChat.getUserId());
        assertEquals("User " + userId, dynamoCapturedChat.getSender());
        assertEquals(message, dynamoCapturedChat.getMessage());


        verify(jedis, times(2))
                .zadd(eq("chat:" + conversationId), any(Double.class), any(String.class));
        verify(jedis, times(2)).expire(eq("chat:" + conversationId), eq(600L));
    }

    @Test
    public void createChatErrorTest() {
        String message = "Message";

        when(authHelpers.getCurrentUserId()).thenReturn(userId);
        when(jedisPool.getResource()).thenThrow(new RuntimeException("Redis connection failed"));

        assertThrows(
                RuntimeException.class,
                () -> chatService.createChat(conversationId, message)
        );

    }

    @Test
    public void testCreateResponse() {
        when(jedisPool.getResource()).thenReturn(jedis);
        UUID chatId = UUID.randomUUID();
        chatService.createResponse(conversationId);


    }

    @Test
    public void testGetCurrentMessagesError() {
        when(jedisPool.getResource()).thenThrow(new RuntimeException("Redis connection failed"));

        List<Chat> response = chatService.getCurrentMessages(conversationId);

        assertEquals(0, response.size());
    }

    @Test
    public void testGetCurrentMessagesSuccess() throws JsonProcessingException {
        when(jedisPool.getResource()).thenReturn(jedis);

        Chat testChat = new Chat(conversationId, UUID.randomUUID(), userId,
                "time", "Test Sender", "Test Message");

        String chatJson = objectMapper.writeValueAsString(testChat);

        when(jedis.zrange("chat:" + conversationId, 0, -1)).thenReturn(Collections.singletonList(chatJson));
        when(mockObjectMapper.readValue(eq(chatJson), eq(Chat.class))).thenReturn(testChat);

        List<Chat> response = chatService.getCurrentMessages(conversationId);

        assertEquals(1, response.size());
        assertEquals(testChat, response.getFirst());
    }

    @Test
    public void testGetCurrentMessagesReadFailure() throws JsonProcessingException {
        when(jedisPool.getResource()).thenReturn(jedis);

        Chat testChat = new Chat(conversationId, UUID.randomUUID(), userId,
                "time", "Test Sender", "Test Message");

        String chatJson = objectMapper.writeValueAsString(testChat);

        when(jedis.zrange("chat:" + conversationId, 0L, -1)).thenReturn(Collections.singletonList(chatJson));
        when(mockObjectMapper.readValue(eq(chatJson), eq(Chat.class))).thenThrow(new RuntimeException("Redis connection failed"));

        List<Chat> response = chatService.getCurrentMessages(conversationId);

        assertEquals(0, response.size());
    }

    @Test
    public void testClearCurrentConversation() {
        when(jedisPool.getResource()).thenReturn(jedis);

        chatService.clearCurrentConversation(conversationId);

        verify(jedis, times(1)).del("chat:" + conversationId);
    }

    @Test
    public void testClearCurrentConversationError() {
        when(jedisPool.getResource()).thenThrow(new RuntimeException("Redis connection failed"));

        assertThrows(
                RuntimeException.class,
                () -> chatService.clearCurrentConversation(conversationId)
        );

        verify(jedis, times(0)).del("chat:" + conversationId);
    }

}
