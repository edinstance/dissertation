package com.finalproject.backend.chats.subscriptions;

import com.finalproject.backend.chats.streams.ChatStream;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;

@DgsComponent
public class ChatSubscriptions {

    private final ChatStream chatStream;

    private final AuthHelpers authHelpers;

    @Autowired
    public ChatSubscriptions(ChatStream chatStream, AuthHelpers authHelpers) {
        this.chatStream = chatStream;
        this.authHelpers = authHelpers;
    }

    @DgsSubscription
    public Publisher<Chat> chatSubscription() {
        UUID userId = authHelpers.getCurrentUserId();

        return chatStream.getChatStream()
                .filter(chat -> chat.getUserId().equals(userId));
    }
}
