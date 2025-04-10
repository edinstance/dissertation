package com.finalproject.backend.ChatTests.QueryTests;

import com.finalproject.backend.chats.queries.ChatQueries;
import com.finalproject.backend.chats.services.ChatService;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.UUID;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatQueryTests {

    @Mock
    private ChatService chatService;

    @InjectMocks
    private ChatQueries chatQueries;

    @Test
    public void testGetCurrentConversation(){
        UUID chatId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Chat chat = new Chat(chatId, userId, "date",
                "User", "Message");
        when(chatService.getCurrentMessages()).thenReturn(List.of(chat));

        List<Chat> response = chatQueries.getCurrentConversation();

        assert response.size() == 1;
        assert response.getFirst().getChatId().equals(chatId);
        assert response.getFirst().getUserId().equals(userId);
        assert response.getFirst().getCreatedAt().equals("date");
        assert response.getFirst().getSender().equals("User");
        assert response.getFirst().getMessage().equals("Message");
    }
}
