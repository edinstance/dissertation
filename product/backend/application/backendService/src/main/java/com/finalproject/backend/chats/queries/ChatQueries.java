package com.finalproject.backend.chats.queries;

import com.finalproject.backend.chats.services.ChatService;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.UUID;

@DgsComponent
public class ChatQueries {

    private final ChatService chatService;

    @Autowired
    public ChatQueries(ChatService chatService) {
        this.chatService = chatService;
    }

    @DgsQuery
    public List<Chat> getCurrentConversation(
            @InputArgument String conversationId) {

        return chatService.getCurrentMessages(UUID.fromString(conversationId));
    }
}
