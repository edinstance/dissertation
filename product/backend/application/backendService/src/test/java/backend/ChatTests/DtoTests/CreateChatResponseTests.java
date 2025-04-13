package backend.ChatTests.DtoTests;

import backend.chats.dto.CreateChatResponse;
import backend.common.dynamodb.tables.Chat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateChatResponseTests {

  private CreateChatResponse createChatResponse;
  UUID conversationID = UUID.randomUUID();
  private final Chat chat = new Chat(
          conversationID,
          UUID.randomUUID(),
          UUID.randomUUID(),
          "String",
          "User",
          "Message"
  );
  private final Chat response = new Chat(
          conversationID,
          UUID.randomUUID(),
          null,
          "String",
          "System",
          "Message"
  );

  @Test
  public void testDefaultConstructor() {
    CreateChatResponse createChatResponse = new CreateChatResponse();

    assertNotNull(createChatResponse);
  }

  @Test
  public void testConstructor() {
    CreateChatResponse createChatResponse = new CreateChatResponse(chat, response);

    assertNotNull(createChatResponse);
    assert createChatResponse.getChat().equals(chat);
    assert createChatResponse.getResponse().equals(response);
  }

  @BeforeEach
  void setUp() {
    createChatResponse = new CreateChatResponse(chat, response);
  }

  @Test
  public void testChatMethods() {
    assert createChatResponse.getChat().equals(chat);

    createChatResponse.setChat(response);
    assert createChatResponse.getChat().equals(response);
  }

  @Test
  public void testResponseMethods() {
    assert createChatResponse.getResponse().equals(response);

    createChatResponse.setResponse(chat);
    assert createChatResponse.getResponse().equals(chat);
  }
}
