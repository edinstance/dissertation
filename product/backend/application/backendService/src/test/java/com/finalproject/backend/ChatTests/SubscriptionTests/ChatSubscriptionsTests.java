package com.finalproject.backend.ChatTests.SubscriptionTests;

import com.finalproject.backend.chats.streams.ChatStream;
import com.finalproject.backend.chats.subscriptions.ChatSubscriptions;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import com.finalproject.backend.common.helpers.AuthHelpers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ChatSubscriptionsTests {

    @Autowired
    private ChatSubscriptions chatSubscriptions;

    @MockBean
    private ChatStream chatStream;

    @MockBean
    private AuthHelpers authHelpers;

    private final UUID chatId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final Chat chat = new Chat(chatId, userId, "time", "sender", "message");


    @Test
    public void testCreateChat() {
        when(authHelpers.getCurrentUserId()).thenReturn(userId);
        when(chatStream.getChatStream()).thenReturn(Flux.just(chat));

        Flux<Chat> filteredChatStream = (Flux<Chat>) chatSubscriptions.chatSubscription();
        List<Chat> chatList = filteredChatStream.collectList().block();

        assert chatList != null;
        assertEquals(1, chatList.size());
        assertEquals(userId, chatList.getFirst().getUserId());
        assertEquals("message", chatList.getFirst().getMessage());

    }
    @Test
    void testChatSubscriptionSpecificUser() {

        Chat chat2 = new Chat(UUID.randomUUID(), UUID.randomUUID(), "time", "sender", "message2");

        when(authHelpers.getCurrentUserId()).thenReturn(userId);
        when(chatStream.getChatStream()).thenReturn(Flux.just(chat, chat2));

        Flux<Chat> filteredChatStream = (Flux<Chat>) chatSubscriptions.chatSubscription();
        List<Chat> chatList = filteredChatStream.collectList().block();

        assert chatList != null;
        assertEquals(1, chatList.size());
        assertEquals(userId, chatList.getFirst().getUserId());
        assertEquals("message", chatList.getFirst().getMessage());
    }
}
