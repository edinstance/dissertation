package com.finalproject.backend.CommonTests.ConfigTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RedisConfigTest {

  @Mock
  private JedisPool mockJedisPool;

  @Mock
  private Jedis mockJedis;

  @BeforeEach
  void setUp() {
    when(mockJedisPool.getResource()).thenReturn(mockJedis);
  }

  @AfterEach
  void tearDown() {
    reset(mockJedisPool, mockJedis);
  }

  @Test
  public void testJedisPingReturnsPong() {
    // Mock the behavior of the Jedis instance
    when(mockJedis.ping()).thenReturn("PONG");

    // Assert that the JedisPool is not null
    assertNotNull(mockJedisPool);
    // Use the JedisPool to get a Jedis instance
    try (Jedis jedis = mockJedisPool.getResource()) {
      // Ping the Jedis instance and check if the response is "PONG"
      String response = jedis.ping();
      assertNotNull(response);
      assertEquals("PONG", response);
    }

    // Verify the interactions with the JedisPool and Jedis instances
    verify(mockJedisPool, times(1)).getResource();
    verify(mockJedis, times(1)).ping();
  }

  @Test
  public void testJedisResourceHandlesException() {
    // Check if the Jedis instance throws an exception when pinged
    when(mockJedisPool.getResource()).thenThrow(new JedisConnectionException("Could not get a resource from the pool"));

    // Assert that the JedisPool is not null
    assertNotNull(mockJedisPool);
    // Use the JedisPool to get a Jedis instance
    assertThrows(JedisConnectionException.class, () -> {
      mockJedisPool.getResource();
    });

    // Verify the interactions with the JedisPool and Jedis instances
    verify(mockJedisPool, times(1)).getResource();
  }

  @Test
  public void testJedisPingHandlesException() {
    // Check if the Jedis instance throws an exception when pinged
    when(mockJedis.ping()).thenThrow(new RuntimeException("Redis not available"));

    // Assert that the JedisPool is not null
    assertNotNull(mockJedisPool);
    // Use the JedisPool to get a Jedis instance
    try (Jedis jedis = mockJedisPool.getResource()) {
      // Ping the Jedis instance and check if an exception is thrown
      assertThrows(RuntimeException.class, jedis::ping);
    }

    // Verify the interactions with the JedisPool and Jedis instances
    verify(mockJedisPool, times(1)).getResource();
    verify(mockJedis, times(1)).ping();
  }
}
