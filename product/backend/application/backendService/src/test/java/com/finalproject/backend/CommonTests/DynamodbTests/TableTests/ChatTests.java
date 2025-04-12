package com.finalproject.backend.CommonTests.DynamodbTests.TableTests;

import com.finalproject.backend.common.dynamodb.tables.Chat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChatTests {

  private Chat chat;

  @Test
  public void testDefaultConstructor() {
    chat = new Chat();
    assertNotNull(chat);
  }

  @Test
  public void testConstructor() {
    Instant now = Instant.now();
    String timestamp = now.toString();

    chat = new Chat(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), timestamp, "User", "Message");

    assertNotNull(chat);
    assertNotNull(chat.getChatId());
    assertEquals(timestamp, chat.getCreatedAt());
    assertNotNull(chat.getMessage());
    assertNotNull(chat.getSender());
  }

  @BeforeEach
  public void setUp() {
    chat = new Chat();
  }

  @Test
  public void testChatIdMethods() {
    UUID chatId = UUID.randomUUID();
    chat.setChatId(chatId);
    assertEquals(chatId, chat.getChatId());
  }

  @Test
  public void testTimestampMethods() {
    Instant now = Instant.now();
    String timestamp = now.toString();
    chat.setCreatedAt(timestamp);
    assertEquals(timestamp, chat.getCreatedAt());
  }

  @Test
  public void testMessageMethods() {
    chat.setMessage("New message");
    assertEquals("New message", chat.getMessage());
  }

  @Test
  public void testSenderMethods() {
    chat.setSender("System");
    assertEquals("System", chat.getSender());
  }

  @Test
  public void testTTLTimestampMethods() {
    Instant now = Instant.now();
    long timestamp = now.toEpochMilli();
    chat.setTtlTimestamp(timestamp);
    assertEquals(timestamp, chat.getTtlTimestamp());
  }

}
