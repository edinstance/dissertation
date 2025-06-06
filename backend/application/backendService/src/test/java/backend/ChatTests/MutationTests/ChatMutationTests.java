package backend.ChatTests.MutationTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.chats.dto.CreateChatResponse;
import backend.chats.mutations.ChatMutations;
import backend.chats.services.ChatService;
import backend.common.dto.MutationResponse;
import backend.common.dynamodb.tables.Chat;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ChatMutationTests {

  @Mock
  private ChatService chatService;

  @InjectMocks
  private ChatMutations chatMutations;

  @Test
  public void createChatTest() {
    UUID conversationId = UUID.randomUUID();
    when(chatMutations.createChat(conversationId.toString(), "Message"))
            .thenReturn(new CreateChatResponse(
                    new Chat(),
                    new Chat()
            ));

    CreateChatResponse response = chatMutations.createChat(conversationId.toString(), "Message");

    assertNotNull(response.getChat());
    assertNotNull(response.getResponse());
    verify(chatService, times(1)).createChat(conversationId, "Message");
  }

  @Test
  public void clearCurrentConversationTest() {
    UUID conversationId = UUID.randomUUID();
    MutationResponse response = chatMutations.clearCurrentConversation(conversationId.toString());
    assertTrue(response.isSuccess());
    assert response.getMessage().equals("Conversation cleared successfully");
    verify(chatService, times(1)).clearCurrentConversation(conversationId);
  }
}
