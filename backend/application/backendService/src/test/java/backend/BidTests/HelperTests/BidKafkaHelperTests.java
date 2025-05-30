package backend.BidTests.HelperTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.bids.dto.CreateBidDto;
import backend.bids.helpers.BidKafkaHelpers;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

@ExtendWith(MockitoExtension.class)
public class BidKafkaHelperTests {

  @Mock
  private KafkaTemplate<String, Object> kafkaTemplate;

  @InjectMocks
  private BidKafkaHelpers bidKafkaHelpers;

  private CreateBidDto bidDto;
  private String topic;
  private String key;
  private int maxRetries;
  private long backoffMs;
  private int currentAttempt;

  @BeforeEach
  void setUp() {
    bidDto = new CreateBidDto(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.TEN, "card");
    topic = "Bids";
    key = bidDto.getBidId().toString();
    maxRetries = 3;
    backoffMs = 100L;
    currentAttempt = 0;
  }

  @Test
  void testAttemptSendSuccess() {
    CompletableFuture<SendResult<String, Object>> kafkaResultFuture = new CompletableFuture<>();

    TopicPartition topicPartition = new TopicPartition(topic, 0);
    long timestamp = System.currentTimeMillis();
    long offset = 0L;
    RecordMetadata recordMetadata =
            new RecordMetadata(topicPartition, offset, 100, timestamp, 0, 0);

    SendResult<String, Object> expectedResult = new SendResult<>(null, recordMetadata);

    when(kafkaTemplate.send(eq(topic), eq(key), eq(bidDto))).thenReturn(kafkaResultFuture);
    kafkaResultFuture.complete(expectedResult);

    CompletableFuture<SendResult<String, Object>> resultFuture = new CompletableFuture<>();
    bidKafkaHelpers.attemptSend(topic, key, bidDto, maxRetries, backoffMs, currentAttempt, resultFuture);

    verify(kafkaTemplate, times(1)).send(eq(topic), eq(key), eq(bidDto));

    assertTrue(resultFuture.isDone());
    assertFalse(resultFuture.isCompletedExceptionally());
  }

  @Test
  void testAttemptSendFailureWithRetry() {
    int localMaxRetries = 1;
    CompletableFuture<SendResult<String, Object>> kafkaResultFuture = new CompletableFuture<>();
    Exception exception = new RuntimeException("Kafka send failure");

    when(kafkaTemplate.send(eq(topic), eq(key), eq(bidDto))).thenReturn(kafkaResultFuture);
    kafkaResultFuture.completeExceptionally(exception);

    CompletableFuture<SendResult<String, Object>> resultFuture = new CompletableFuture<>();
    bidKafkaHelpers.attemptSend(topic, key, bidDto, localMaxRetries, backoffMs, currentAttempt, resultFuture);

    verify(kafkaTemplate, times(1)).send(eq(topic), eq(key), eq(bidDto));
  }

  @Test
  void testAttemptSendFailureMaxRetriesExceeded() {
    int localMaxRetries = 0;
    CompletableFuture<SendResult<String, Object>> kafkaResultFuture = new CompletableFuture<>();
    Exception exception = new RuntimeException("Kafka send failure");

    when(kafkaTemplate.send(eq(topic), eq(key), eq(bidDto))).thenReturn(kafkaResultFuture);
    kafkaResultFuture.completeExceptionally(exception);

    CompletableFuture<SendResult<String, Object>> resultFuture = new CompletableFuture<>();
    bidKafkaHelpers.attemptSend(topic, key, bidDto, localMaxRetries, backoffMs, currentAttempt, resultFuture);

    verify(kafkaTemplate, times(1)).send(eq(topic), eq(key), eq(bidDto));

    assertTrue(resultFuture.isDone());
    assertTrue(resultFuture.isCompletedExceptionally());
  }
}
