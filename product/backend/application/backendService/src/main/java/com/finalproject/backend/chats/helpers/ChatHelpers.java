package com.finalproject.backend.chats.helpers;

/**
 * A helper class for the chats.
 */
public class ChatHelpers {

  /**
   * A static function that cleans the response from openAI.
   *
   * @param input the chat to clean.
   * @return the cleaned chat.
   */
  public static String cleanOpenAiChatResponse(String input) {
    if (input.contains("text=")) {
      int startIndex = input.indexOf("text=") + 5;
      int endIndex = input.indexOf(", type=output_text");
      if (endIndex > startIndex) {
        return input.substring(startIndex, endIndex).trim();
      }
    }
    return input;
  }
}
