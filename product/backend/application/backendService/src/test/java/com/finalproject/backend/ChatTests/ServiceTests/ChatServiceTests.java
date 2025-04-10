package com.finalproject.backend.ChatTests.ServiceTests;

import com.finalproject.backend.chats.dynamodb.ChatsDynamoService;
import com.finalproject.backend.chats.services.ChatService;
import com.finalproject.backend.chats.streams.ChatStream;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import com.finalproject.backend.common.helpers.AuthHelpers;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTests {

    @Mock
    private ChatsDynamoService chatsDynamoService;

    @Mock
    private ChatStream chatStream;

    @Mock
    private AuthHelpers authHelpers;

    @InjectMocks
    private ChatService chatService;

    @Test
    public void createChatTests() {
        ArgumentCaptor<Chat> chatCaptor = ArgumentCaptor.forClass(Chat.class);

        UUID userId = UUID.randomUUID();
        when(authHelpers.getCurrentUserId()).thenReturn(userId);

        chatService.createChat("Message");

        verify(chatStream).publish(chatCaptor.capture());

        Chat capturedChat = chatCaptor.getValue();

        assertNotNull(capturedChat);
        assertNotNull(capturedChat.getChatId());
        assertEquals(userId, capturedChat.getUserId());
        assertEquals("System", capturedChat.getSender());
        assertEquals("Message placeholder", capturedChat.getMessage());

        verify(chatsDynamoService, times(1)).writeChat(any(Chat.class));
        verify(chatStream, times(1)).publish(any(Chat.class));
    }

    @Test
    public void testCreateResponse() {
        UUID chatId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Chat response = chatService.createResponse(chatId, userId);

        assertNotNull(response);
        assertEquals(chatId, response.getChatId());
        assertEquals(userId, response.getUserId());
        assertEquals("System", response.getSender());
        assertEquals("Message placeholder", response.getMessage());
    }

}
