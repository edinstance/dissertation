package backend.ChatTests.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import backend.common.dynamodb.tables.Chat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class CreateResponseTests extends SetupChatServiceTests {

  @Test
  public void testOpenApiIsNotEnabled() {
    ReflectionTestUtils.setField(chatService, "isEnabled", false);
    when(jedisPool.getResource()).thenReturn(jedis);

    Chat chat = chatService.createResponse(conversationId, "message");

    assertEquals("Chat is not enabled.", chat.getMessage());
    assertEquals(conversationId, chat.getConversationId());
    assertEquals("System", chat.getSender());
  }

}
