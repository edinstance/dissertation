package com.finalproject.backend.ChatTests.ServiceTests;

import com.finalproject.backend.chats.services.ChatService;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CreateResponseTests {

  @Mock
  private OpenAIClient openAIClient;

  @Mock
  private OpenAIOkHttpClient.Builder builder;

  @InjectMocks
  private ChatService chatService;

  private final UUID conversationId = UUID.randomUUID();

  @Test
  public void testOpenApiIsNotEnabled() {
    ReflectionTestUtils.setField(chatService, "isEnabled", false);

    Chat chat = chatService.createResponse(conversationId, "message");

    assertEquals("Chat is not enabled.", chat.getMessage());
    assertEquals(conversationId, chat.getConversationId());
    assertEquals("System", chat.getSender());
  }

}
