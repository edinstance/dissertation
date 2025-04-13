package backend.ChatTests.HelperTests;

import backend.chats.helpers.ChatHelpers;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatHelperTests {

  @Test
  public void testCleanOpenAiChatResponseValid() {
    String rawOutput = "Some text before text=This is the chat response, " +
            "type=output_text, some text after";
    String expectedOutput = "This is the chat response";
    String actualOutput = ChatHelpers.cleanOpenAiChatResponse(rawOutput);
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testCleanOpenAiChatResponseNoEqualsAtStart() {
    String rawOutput = "Some text without the text equals";
    String actualOutput = ChatHelpers.cleanOpenAiChatResponse(rawOutput);
    assertEquals(rawOutput, actualOutput);
  }

  @Test
  public void testCleanOpenAiChatResponseNoEqualsAtEnd() {
    String rawOutput = "Some text before text=This is the chat response";
    String actualOutput = ChatHelpers.cleanOpenAiChatResponse(rawOutput);
    assertEquals(rawOutput, actualOutput);
  }
}
