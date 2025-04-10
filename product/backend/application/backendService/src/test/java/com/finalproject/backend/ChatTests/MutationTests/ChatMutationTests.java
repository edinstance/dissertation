package com.finalproject.backend.ChatTests.MutationTests;

import com.finalproject.backend.chats.Mutations.ChatMutations;
import com.finalproject.backend.chats.services.ChatService;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
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
        Chat chat = new Chat(UUID.randomUUID(), "Created", "USER", "Message");
        chatMutations.createChat(chat);

        verify(chatService, times(1)).createChat(chat);
    }
}
