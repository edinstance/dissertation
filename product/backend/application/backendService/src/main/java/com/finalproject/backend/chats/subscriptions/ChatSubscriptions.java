package com.finalproject.backend.chats.subscriptions;

import com.finalproject.backend.chats.streams.ChatStream;
import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import java.util.UUID;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A component for the chat subscriptions.
 */
@DgsComponent
public class ChatSubscriptions {

  /**
   * The chat stream to use.
   */
  private final ChatStream chatStream;

  /**
   * Constructor for the subscription.
   *
   * @param chatStream the chat stream.
   */
  @Autowired
  public ChatSubscriptions(ChatStream chatStream) {
    this.chatStream = chatStream;
  }

  /**
   * Subscription for getting chats.
   *
   * @param conversationId the conversation to filter by.
   * @return a publisher of the chats.
   */
  @DgsSubscription
  public Publisher<Chat> chatSubscription(@InputArgument String conversationId) {

    return chatStream.getChatStream()
            .filter(chat -> {
              boolean shouldPublish = chat.getConversationId()
                      .equals(UUID.fromString(conversationId));
              AppLogger.info("Checking chat message: " + chat
                      + ", conversationId: "
                      + conversationId + ", shouldPublish: " + shouldPublish);
              return shouldPublish;
            })
            .doOnSubscribe(s ->
                    AppLogger.info("New subscription to conversation: " + conversationId))
            .doOnCancel(() ->
                    AppLogger.info("Subscription cancelled for conversation: " + conversationId));
  }
}
