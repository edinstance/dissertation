package com.finalproject.backend.ChatTests.ServiceTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateChatTests extends SetupChatServiceTests {

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
}
