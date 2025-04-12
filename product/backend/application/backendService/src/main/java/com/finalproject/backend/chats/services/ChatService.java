package com.finalproject.backend.chats.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.chats.dto.CreateChatResponse;
import com.finalproject.backend.chats.dynamodb.ChatsDynamoService;
import com.finalproject.backend.chats.helpers.ChatHelpers;
import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * A service for handling chats.
 */
@Service
public class ChatService {

  /**
   * The chat dynamo service to use.
   */
  private final ChatsDynamoService chatsDynamoService;

  /**
   * The auth helpers to use.
   */
  private final AuthHelpers authHelpers;

  /**
   * The cache pool.
   */
  private final JedisPool jedisPool;

  /**
   * The object mapper to use.
   */
  private final ObjectMapper objectMapper;

  @Value("${chat.isEnabled:false}")
  private boolean isEnabled;

  @Value("${chat.openApiKey}")
  private String openAiApiKey;

  @Value("${chat.openApiProjectId}")
  private String openAiProject;

  @Value("${chat.openApiOrganizationId}")
  private String openAiOrganization;

  /**
   * The constructor for the service.
   *
   * @param chatsDynamoService the dynamo service to use.
   * @param authHelpers        the auth helpers to use.
   * @param jedisPool          the cache pool.
   * @param objectMapper       the object mapper.
   */
  @Autowired
  public ChatService(ChatsDynamoService chatsDynamoService, AuthHelpers authHelpers,
                     JedisPool jedisPool, ObjectMapper objectMapper) {
    this.chatsDynamoService = chatsDynamoService;
    this.authHelpers = authHelpers;
    this.jedisPool = jedisPool;
    this.objectMapper = objectMapper;
  }

  /**
   * A function to return if the chat is enabled.
   *
   * @return if it is enabled.
   */
  public boolean isEnabled() {
    return isEnabled;
  }

  /**
   * A function to create a chat.
   *
   * @param conversationId the conversation to create the chat in.
   * @param message        the message for the chat.
   */
  public CreateChatResponse createChat(UUID conversationId, String message) {
    Instant now = Instant.now();
    UUID chatId = UUID.randomUUID();
    UUID userId = authHelpers.getCurrentUserId();

    Chat chat = new Chat(
            conversationId,
            chatId,
            userId,
            now.toString(),
            "User " + userId,
            message
    );

    addChatToCache(conversationId, now, chat);

    chatsDynamoService.writeChat(chat);

    return new CreateChatResponse(chat, createResponse(conversationId, message));
  }

  /**
   * A function to create a response to a chat.
   *
   * @param conversationId the id of the conversation
   */
  public Chat createResponse(final UUID conversationId, final String message) {
    Instant now = Instant.now();
    Chat chat = new Chat(conversationId, UUID.randomUUID(), null, now.toString(), "System");

    try {
      if (!isEnabled) {
        chat.setConversationId(conversationId);
        chat.setMessage("Chat is not enabled.");
        chat.setCreatedAt(now.toString());
        return chat;
      }
      OpenAIClient client = OpenAIOkHttpClient.builder()
              .apiKey(openAiApiKey)
              .project(openAiProject)
              .organization(openAiOrganization)
              .build();

      String prompt =
              "You are a friendly, helpful assistant for an "
                      + "online shopping platform called ShopSmart. "
                      + "Your name is SmartShop Assistant. "
                      + "Respond in a warm, conversational tone while being concise and accurate. "
                      + "Address the user directly and personalize your responses. "
                      + "If you cannot answer based on your knowledge, politely "
                      + "explain you're unable to help "
                      + "and suggest contacting customer service. "
                      + "End your response with a follow-up question or "
                      + "offer of additional help when appropriate. "
                      + "User's Question: "
                      + message;

      ResponseCreateParams params =
              ResponseCreateParams.builder().input(prompt).model(ChatModel.GPT_4O_MINI).build();

      Response response = client.responses().create(params);

      String aiResponseText = response.output().toString();
      aiResponseText = ChatHelpers.cleanOpenAiChatResponse(aiResponseText);
      AppLogger.info("AI Response: " + aiResponseText);

      chat.setMessage(aiResponseText);

      addChatToCache(conversationId, now, chat);
      return chat;

    } catch (Exception e) {
      AppLogger.error("Error generating AI response:", e);
      chat.setMessage("Error generating getting response. Please try again later.");
      return chat;
    }
  }

  private void addChatToCache(UUID conversationId, Instant now, Chat chat) {
    try (Jedis jedis = jedisPool.getResource()) {

      jedis.zadd("chat:" + conversationId, now.toEpochMilli(),
              objectMapper.writeValueAsString(chat));

      jedis.expire("chat:" + conversationId, 600L);
    } catch (Exception e) {
      AppLogger.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  /**
   * A function to clear the current conversation.
   *
   * @param conversationId the id of the conversation.
   */
  public void clearCurrentConversation(UUID conversationId) {
    try (Jedis jedis = jedisPool.getResource()) {
      jedis.del("chat:" + conversationId);
    } catch (Exception e) {
      AppLogger.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  /**
   * A function to get the current messages.
   *
   * @param conversationId the id of the conversation.
   *
   * @return the list of messages.
   */
  public List<Chat> getCurrentMessages(UUID conversationId) {
    List<Chat> messages = new ArrayList<>();
    try (Jedis jedis = jedisPool.getResource()) {
      String chatKey = "chat:" + conversationId;

      List<String> messageJsonSet = jedis.zrange(chatKey, 0, -1);

      for (String messageJson : messageJsonSet) {
        try {
          Chat chat = objectMapper.readValue(messageJson, Chat.class);
          messages.add(chat);
        } catch (Exception e) {
          AppLogger.error(e.getMessage());
          return new ArrayList<>();
        }
      }

      return messages;

    } catch (Exception e) {
      AppLogger.error(e.getMessage());
      return new ArrayList<>();
    }
  }
}
