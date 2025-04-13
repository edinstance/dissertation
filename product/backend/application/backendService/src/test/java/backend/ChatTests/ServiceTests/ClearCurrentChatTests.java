package backend.ChatTests.ServiceTests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClearCurrentChatTests extends SetupChatServiceTests {

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
