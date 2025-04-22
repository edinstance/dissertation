package backend.ChatTests.QueryTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import backend.chats.queries.ChatQueries;
import backend.chats.services.ChatService;
import backend.common.dynamodb.tables.Chat;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ChatQueryTests {

  @Mock
  private ChatService chatService;

  @InjectMocks
  private ChatQueries chatQueries;

  @Test
  public void testGetCurrentConversation() {
    UUID conversationId = UUID.randomUUID();
    UUID chatId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    Chat chat = new Chat(conversationId, chatId, userId,
            "date",
            "User", "Message");
    when(chatService.getCurrentMessages(conversationId)).thenReturn(List.of(chat));

    List<Chat> response = chatQueries.getCurrentConversation(conversationId.toString());

    assert response.size() == 1;
    assert response.getFirst().getChatId().equals(chatId);
    assert response.getFirst().getUserId().equals(userId);
    assert response.getFirst().getCreatedAt().equals("date");
    assert response.getFirst().getSender().equals("User");
    assert response.getFirst().getMessage().equals("Message");
  }

  @Test
  public void testIsChatEnabled() {
    when(chatService.isEnabled()).thenReturn(true);

    boolean isEnabled = chatQueries.isChatEnabled();

    assertTrue(isEnabled);

    when(chatService.isEnabled()).thenReturn(false);

    isEnabled = chatQueries.isChatEnabled();

    assertFalse(isEnabled);
  }
}
