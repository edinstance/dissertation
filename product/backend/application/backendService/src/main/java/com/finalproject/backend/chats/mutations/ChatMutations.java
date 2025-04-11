package com.finalproject.backend.chats.mutations;

import com.finalproject.backend.chats.services.ChatService;
import com.finalproject.backend.common.dto.MutationResponse;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class contains the chat mutations.
 */
@DgsComponent
public class ChatMutations {

  /**
   * The chat service to interact with.
   */
  private final ChatService chatService;

  /**
   * Constructor for the mutations.
   *
   * @param chatService the service to use.
   */
  @Autowired
  public ChatMutations(ChatService chatService) {
    this.chatService = chatService;
  }

  /**
   * A mutation to create a chat.
   *
   * @param conversationId the id of the conversation.
   * @param message        the message for the chat.
   */
  @DgsMutation
  public MutationResponse createChat(@InputArgument String conversationId,
                                     @InputArgument String message) {
    chatService.createChat(UUID.fromString(conversationId), message);

    return new MutationResponse(true,
            "Chat created successfully");
  }


  /**
   * A mutation to clear the current conversation of a user.
   *
   * @return a response on the result.
   */
  @DgsMutation
  public MutationResponse clearCurrentConversation(
          @InputArgument String conversationId) {

    chatService.clearCurrentConversation(UUID.fromString(conversationId));

    return new MutationResponse(true,
            "Conversation cleared successfully");
  }
}
