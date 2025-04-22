package backend.chats.dto;

import backend.common.dynamodb.tables.Chat;
import lombok.Getter;
import lombok.Setter;

/**
 * Dto for creating chat response.
 */
@Getter
@Setter
public class CreateChatResponse {

  /**
   * The chat that was created.
   */
  private Chat chat;

  /**
   * The response to the chat.
   */
  private Chat response;

  /**
   * Default constructor.
   */
  public CreateChatResponse() {
  }

  /**
   * Constructor with details.
   *
   * @param chat     the chat.
   * @param response the response.
   */
  public CreateChatResponse(Chat chat, Chat response) {
    this.chat = chat;
    this.response = response;
  }
}
