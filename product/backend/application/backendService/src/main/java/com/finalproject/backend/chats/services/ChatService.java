package com.finalproject.backend.chats.services;

import com.finalproject.backend.chats.dynamodb.ChatsDynamoService;
import com.finalproject.backend.chats.streams.ChatStream;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import com.finalproject.backend.common.helpers.AuthHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.UUID;

@Service
public class ChatService {

    private final ChatsDynamoService chatsDynamoService;

    private final ChatStream chatStream;

    private final AuthHelpers authHelpers;

    @Autowired
    public ChatService(ChatsDynamoService chatsDynamoService, ChatStream chatStream, AuthHelpers authHelpers) {
        this.chatsDynamoService = chatsDynamoService;
        this.chatStream = chatStream;
        this.authHelpers = authHelpers;
    }

    public void createChat(String message) {
        Instant now = Instant.now();
        UUID chatId = UUID.randomUUID();
        UUID userId = authHelpers.getCurrentUserId();

        Chat chat = new Chat(
                chatId,
                userId,
                now.toString(),
                "User " + userId,
                message
        );

        chatsDynamoService.writeChat(chat);

        Chat response = createResponse(chatId, chat.getUserId());
        chatStream.publish(response);
    }

    public Chat createResponse(final UUID chatId, final UUID userId) {
        Instant now = Instant.now();
        return new Chat(chatId, userId, now.toString(),
                "System", "Message placeholder");
    }
}
