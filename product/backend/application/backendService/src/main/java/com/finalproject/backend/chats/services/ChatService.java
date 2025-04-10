package com.finalproject.backend.chats.services;

import com.finalproject.backend.chats.dynamodb.ChatsDynamoService;
import com.finalproject.backend.chats.streams.ChatStream;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.UUID;

@Service
public class ChatService {

    private final ChatsDynamoService chatsDynamoService;

    private final ChatStream chatStream;

    @Autowired
    public ChatService(ChatsDynamoService chatsDynamoService, ChatStream chatStream) {
        this.chatsDynamoService = chatsDynamoService;
        this.chatStream = chatStream;
    }

    public void createChat(Chat chat) {
        Instant now = Instant.now();
        UUID chatId = UUID.randomUUID();

        chat.setCreatedAt(now.toString());
        chat.setChatId(chatId);

        chatsDynamoService.writeChat(chat);

        Chat response = createResponse(chatId);
        chatStream.publish(response);
    }

    public Chat createResponse(final UUID chatId) {
        Instant now = Instant.now();
        return new Chat(chatId, now.toString(), "System", "Message placeholder");
    }
}
