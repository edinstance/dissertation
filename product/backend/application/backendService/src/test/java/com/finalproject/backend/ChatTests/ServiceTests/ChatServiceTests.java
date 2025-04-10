package com.finalproject.backend.ChatTests.ServiceTests;

import com.finalproject.backend.chats.dynamodb.ChatsDynamoService;
import com.finalproject.backend.chats.services.ChatService;
import com.finalproject.backend.chats.streams.ChatStream;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Instant;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTests {

    @Mock
    private ChatsDynamoService chatsDynamoService;

    @Mock
    private ChatStream chatStream;

    @InjectMocks
    private ChatService chatService;

    private Chat chat;

    @BeforeEach
    void setUp() {
        chat = new Chat(UUID.randomUUID(), Instant.now().toString(),
                "System", "Message");
    }

    @Test
    public void createChatTests() {
        ArgumentCaptor<Chat> chatCaptor = ArgumentCaptor.forClass(Chat.class);

        chatService.createChat(chat);

        verify(chatStream).publish(chatCaptor.capture());

        Chat capturedChat = chatCaptor.getValue();

        assertNotNull(capturedChat);
        assertEquals(chat.getChatId(), capturedChat.getChatId());
        assertEquals("System", capturedChat.getSender());
        assertEquals("Message placeholder", capturedChat.getMessage());

        verify(chatsDynamoService, times(1)).writeChat(chat);
        verify(chatStream, times(1)).publish(any(Chat.class));
    }

    @Test
    public void testCreateResponse() {
        UUID chatId = UUID.randomUUID();
        Chat response = chatService.createResponse(chatId);

        assertNotNull(response);
        assertEquals(chatId, response.getChatId());
        assertEquals("System", response.getSender());
        assertEquals("Message placeholder", response.getMessage());
    }

}
