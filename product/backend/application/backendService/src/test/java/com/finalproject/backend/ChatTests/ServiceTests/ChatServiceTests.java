package com.finalproject.backend.ChatTests.ServiceTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.chats.dynamodb.ChatsDynamoService;
import com.finalproject.backend.chats.services.ChatService;
import com.finalproject.backend.chats.streams.ChatStream;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTests {

    @Mock
    private ChatsDynamoService chatsDynamoService;

    @Mock
    private ChatStream chatStream;

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

    private final UUID userId = UUID.randomUUID();

    @Test
    public void createChatTest() throws JsonProcessingException {
        ArgumentCaptor<Chat> chatCaptor = ArgumentCaptor.forClass(Chat.class);


        String message = "Message";

        when(authHelpers.getCurrentUserId()).thenReturn(userId);
        when(jedisPool.getResource()).thenReturn(jedis);

        when(mockObjectMapper.writeValueAsString(any(Chat.class)))
                .thenAnswer(
                        invocation -> {
                            Chat chat = invocation.getArgument(0);
                            return objectMapper.writeValueAsString(chat);
                        });

        chatService.createChat(message);

        verify(chatStream).publish(chatCaptor.capture());

        Chat capturedChat = chatCaptor.getValue();

        assertNotNull(capturedChat);
        assertNotNull(capturedChat.getChatId());
        assertEquals(userId, capturedChat.getUserId());
        assertEquals("System", capturedChat.getSender());
        assertEquals("Message placeholder", capturedChat.getMessage());

        verify(chatsDynamoService, times(1)).writeChat(any(Chat.class));
        verify(chatStream, times(1)).publish(any(Chat.class));
        verify(jedis, times(1))
                .zadd(eq("chat:" + userId), any(Double.class), any(String.class));
        verify(jedis, times(1)).expire(eq("chat:" + userId), eq(600L));
    }

    @Test
    public void createChatErrorTest() {
        String message = "Message";

        when(authHelpers.getCurrentUserId()).thenReturn(userId);
        when(jedisPool.getResource()).thenThrow(new RuntimeException("Redis connection failed"));

        assertThrows(
                RuntimeException.class,
                () -> chatService.createChat(message)
        );

    }


    @Test
    public void testCreateResponse() {
        UUID chatId = UUID.randomUUID();
        Chat response = chatService.createResponse(chatId, userId);

        assertNotNull(response);
        assertEquals(chatId, response.getChatId());
        assertEquals(userId, response.getUserId());
        assertEquals("System", response.getSender());
        assertEquals("Message placeholder", response.getMessage());
    }

    @Test
    public void testGetCurrentMessagesError() {
        when(jedisPool.getResource()).thenThrow(new RuntimeException("Redis connection failed"));

        List<Chat> response = chatService.getCurrentMessages();

        assertEquals(0, response.size());
    }

    @Test
    public void testGetCurrentMessagesSuccess() throws JsonProcessingException {
        when(jedisPool.getResource()).thenReturn(jedis);
        when(authHelpers.getCurrentUserId()).thenReturn(userId);

        Chat testChat = new Chat(UUID.randomUUID(), userId, "time", "Test Sender", "Test Message");

        String chatJson = objectMapper.writeValueAsString(testChat);

        when(jedis.zrange("chat:" + userId, 0, -1)).thenReturn(Collections.singletonList(chatJson));
        when(mockObjectMapper.readValue(eq(chatJson), eq(Chat.class))).thenReturn(testChat);

        List<Chat> response = chatService.getCurrentMessages();

        assertEquals(1, response.size());
        assertEquals(testChat, response.getFirst());
    }

    @Test
    public void testGetCurrentMessagesReadFailure() throws JsonProcessingException {
        when(jedisPool.getResource()).thenReturn(jedis);
        when(authHelpers.getCurrentUserId()).thenReturn(userId);

        Chat testChat = new Chat(UUID.randomUUID(), userId, "time", "Test Sender", "Test Message");

        String chatJson = objectMapper.writeValueAsString(testChat);

        when(jedis.zrange("chat:" + userId, 0, -1)).thenReturn(Collections.singletonList(chatJson));
        when(mockObjectMapper.readValue(eq(chatJson), eq(Chat.class))).thenThrow(new RuntimeException("Redis connection failed"));

        List<Chat> response = chatService.getCurrentMessages();

        assertEquals(0, response.size());
    }

    @Test
    public void testClearCurrentConversation() {
        when(jedisPool.getResource()).thenReturn(jedis);
        when(authHelpers.getCurrentUserId()).thenReturn(userId);

        chatService.clearCurrentConversation();

        verify(jedis, times(1)).del("chat:" + userId);
    }

    @Test
    public void testClearCurrentConversationError() {
        when(jedisPool.getResource()).thenThrow(new RuntimeException("Redis connection failed"));

        assertThrows(
                RuntimeException.class,
                () -> chatService.clearCurrentConversation()
        );

        verify(jedis, times(0)).del("chat:" + userId);
    }

}
