package com.finalproject.backend.ChatTests.MutationTests;

import com.finalproject.backend.chats.Mutations.ChatMutations;
import com.finalproject.backend.chats.services.ChatService;
import com.finalproject.backend.common.dto.MutationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ChatMutationTests {

    @Mock
    private ChatService chatService;

    @InjectMocks
    private ChatMutations chatMutations;

    @Test
    public void createChatTest() {
        UUID conversationId = UUID.randomUUID();
        MutationResponse response = chatMutations.createChat(conversationId.toString(), "Message");

        assertTrue(response.isSuccess());
        assert response.getMessage().equals("Chat created successfully");
        verify(chatService, times(1)).createChat(conversationId, "Message");
    }
}
