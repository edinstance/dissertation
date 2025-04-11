package com.finalproject.backend.chats.subscriptions;

import com.finalproject.backend.chats.streams.ChatStream;
import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import java.time.Duration;
import java.util.UUID;
import static java.util.Locale.filter;

@DgsComponent
public class ChatSubscriptions {

    private final ChatStream chatStream;

    @Autowired
    public ChatSubscriptions(ChatStream chatStream) {
        this.chatStream = chatStream;
    }

    @DgsSubscription
    public Publisher<Chat> chatSubscription(@InputArgument String conversationId) {

        return chatStream.getChatStream()
                .filter(chat -> {
                    boolean shouldPublish = chat.getConversationId()
                            .equals(UUID.fromString(conversationId));
                    AppLogger.info("Checking chat message: " + chat
                            + ", conversationId: " + conversationId + ", shouldPublish: " + shouldPublish);
                    return shouldPublish;
                })
                .doOnSubscribe(s -> AppLogger.info("New subscription to conversation: " + conversationId))
                .doOnCancel(() -> AppLogger.info("Subscription cancelled for conversation: " + conversationId));
    }
}
