package backend.chats.queries;

import backend.chats.services.ChatService;
import backend.common.dynamodb.tables.Chat;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A component for the chat queries.
 */
@DgsComponent
public class ChatQueries {

  /**
   * The chat service to use.
   */
  private final ChatService chatService;

  /**
   * Constructor for the query.
   *
   * @param chatService the service to use.
   */
  @Autowired
  public ChatQueries(ChatService chatService) {
    this.chatService = chatService;
  }

  /**
   * A query to get the current conversation.
   *
   * @param conversationId the conversation id.
   *
   * @return the chats in the conversation.
   */
  @DgsQuery
  public List<Chat> getCurrentConversation(
          @InputArgument String conversationId) {

    return chatService.getCurrentMessages(UUID.fromString(conversationId));
  }

  /**
   * A query to get if the chat is enabled.
   *
   * @return if the chat is enabled.
   */
  @DgsQuery
  public boolean isChatEnabled() {
    return chatService.isEnabled();
  }
}
