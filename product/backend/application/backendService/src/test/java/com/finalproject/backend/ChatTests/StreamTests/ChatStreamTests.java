package com.finalproject.backend.ChatTests.StreamTests;

import com.finalproject.backend.chats.streams.ChatStream;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Sinks;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatStreamTests {

    @Mock
    private Sinks.Many<Chat> chatSink;

    @InjectMocks
    private ChatStream chatStream;

    @Test
    public void publishMessageTest() throws InterruptedException { //Handle interrupted exception.
        ChatStream chatStream = new ChatStream();
        UUID conversationId = UUID.randomUUID();
        UUID chatId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Chat chat = new Chat(conversationId, chatId, userId, "testTimestamp", "testSender", "testMessage");

        BlockingQueue<Chat> queue = new LinkedBlockingQueue<>();
        chatStream.getChatStream().subscribe(queue::add);
        chatStream.publish(chat);

        Chat receivedChat = queue.poll(1, TimeUnit.SECONDS);

        assertNotNull(receivedChat);

        assertNotNull(receivedChat.getChatId());
        assertNotNull(receivedChat.getCreatedAt());
        assertEquals(chatId, receivedChat.getChatId());
        assertEquals(userId, receivedChat.getUserId());
        assertEquals("testTimestamp", receivedChat.getCreatedAt());
        assertEquals("testSender", receivedChat.getSender());
        assertEquals("testMessage", receivedChat.getMessage());
    }
}
