package com.finalproject.backend.ChatTests.StreamTests;

import com.finalproject.backend.chats.streams.ChatStream;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChatStreamTests {

    @Test
    public void publishMessageTest() throws InterruptedException { //Handle interrupted exception.
        ChatStream chatStream = new ChatStream();
        UUID chatId = UUID.randomUUID();
        Chat chat = new Chat(chatId, "testTimestamp", "testSender", "testMessage");

        BlockingQueue<Chat> queue = new LinkedBlockingQueue<>();
        chatStream.getChatStream().subscribe(queue::add);
        chatStream.publish(chat);

        Chat receivedChat = queue.poll(1, TimeUnit.SECONDS);

        assertNotNull(receivedChat);

        assertNotNull(receivedChat.getChatId());
        assertNotNull(receivedChat.getCreatedAt());
        assertEquals(chatId, receivedChat.getChatId());
        assertEquals("testTimestamp", receivedChat.getCreatedAt());
        assertEquals("testSender", receivedChat.getSender());
        assertEquals("testMessage", receivedChat.getMessage());
    }
}
