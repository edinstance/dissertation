package com.finalproject.backend.ChatTests.ServiceTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.chats.dynamodb.ChatsDynamoService;
import com.finalproject.backend.chats.services.ChatService;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.openai.client.OpenAIClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.text.ParseException;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class SetupChatServiceTests {

  @Mock
  public ChatsDynamoService chatsDynamoService;

  @Mock
  public AuthHelpers authHelpers;

  @Mock
  public JedisPool jedisPool;

  @Mock
  public Jedis jedis;

  @Mock
  public ObjectMapper mockObjectMapper;

  @InjectMocks
  public ChatService chatService;

  public ObjectMapper objectMapper;
  public UUID conversationId;
  public UUID userId;


  @BeforeEach
  public void setUp() throws ParseException {
    objectMapper = new ObjectMapper();
    conversationId = UUID.randomUUID();
    userId = UUID.randomUUID();
  }
}
