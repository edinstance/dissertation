package com.finalproject.backend.ChatTests.ServiceTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class GetCurrentMessagesTests extends SetupChatServiceTests {

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
}
