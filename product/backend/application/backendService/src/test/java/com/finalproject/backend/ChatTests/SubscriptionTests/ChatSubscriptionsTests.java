package com.finalproject.backend.ChatTests.SubscriptionTests;

import com.finalproject.backend.chats.streams.ChatStream;
import com.finalproject.backend.chats.subscriptions.ChatSubscriptions;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import com.finalproject.backend.common.helpers.AuthHelpers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatSubscriptionsTests {

    @Mock
    private ChatStream chatStream;

    @Mock
    private AuthHelpers authHelpers;

    @InjectMocks
    private ChatSubscriptions chatSubscriptions;

    private final UUID conversationId = UUID.randomUUID();
    private final UUID chatId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final Chat chat = new Chat(conversationId, chatId, userId, "time", "sender", "message");


    @Test
    public void testCreateChat() {
        when(chatStream.getChatStream()).thenReturn(Flux.just(chat));

        Flux<Chat> filteredChatStream = (Flux<Chat>) chatSubscriptions.chatSubscription(conversationId.toString());
        List<Chat> chatList = filteredChatStream.collectList().block();

        assert chatList != null;
        assertEquals(1, chatList.size());
        assertEquals(userId, chatList.getFirst().getUserId());
        assertEquals("message", chatList.getFirst().getMessage());
    }

    @Test
    public void testCreateChatFilteredByConversationId() {

        Chat newChat = new Chat(UUID.randomUUID(), chatId, userId, "time", "sender", "message");

        when(chatStream.getChatStream()).thenReturn(Flux.just(chat, newChat));

        Flux<Chat> filteredChatStream = (Flux<Chat>) chatSubscriptions.chatSubscription(conversationId.toString());
        List<Chat> chatList = filteredChatStream.collectList().block();

        assert chatList != null;
        assertEquals(1, chatList.size());
        assertEquals(userId, chatList.getFirst().getUserId());
        assertEquals("message", chatList.getFirst().getMessage());
    }
}
