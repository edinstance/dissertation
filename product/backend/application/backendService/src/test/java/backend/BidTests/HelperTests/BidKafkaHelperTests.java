package backend.BidTests.HelperTests;

import backend.bids.dto.CreateBidDto;
import backend.bids.helpers.BidKafkaHelpers;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import java.util.concurrent.CompletableFuture;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BidKafkaHelperTests {

  @Mock
  private KafkaTemplate<String, CreateBidDto> bidKafkaTemplate;

  @InjectMocks
  private BidKafkaHelpers bidKafkaHelpers;

  @Test
  void testAttemptSendSuccess() {
    CreateBidDto bidDto = new CreateBidDto();
    int maxRetries = 3;
    long backoffMs = 100L;
    int currentAttempt = 0;
    CompletableFuture<SendResult<String, CreateBidDto>> resultFuture =
            new CompletableFuture<>();
    CompletableFuture<SendResult<String, CreateBidDto>> kafkaResultFuture =
            new CompletableFuture<>();

    TopicPartition topicPartition = new TopicPartition("Bids", 0);
    long timestamp = System.currentTimeMillis();
    long offset = 0L;
    RecordMetadata recordMetadata = new RecordMetadata(
            topicPartition,
            offset,
            100,
            timestamp,
            0,
            0
    );

    SendResult<String, CreateBidDto> expectedResult = new SendResult<>(null, recordMetadata);

    when(bidKafkaTemplate.send(eq("Bids"), eq(bidDto))).thenReturn(kafkaResultFuture);
    kafkaResultFuture.complete(expectedResult);

    bidKafkaHelpers.attemptSend(bidDto, maxRetries, backoffMs, currentAttempt, resultFuture);

    verify(bidKafkaTemplate, times(1)).send(eq("Bids"), eq(bidDto));

    assert resultFuture.isDone();
    assert !resultFuture.isCompletedExceptionally();
  }

  @Test
  void testAttemptSendFailureWithRetry() {
    CreateBidDto bidDto = new CreateBidDto();
    int maxRetries = 1;
    long backoffMs = 100L;
    int currentAttempt = 0;
    CompletableFuture<SendResult<String, CreateBidDto>> resultFuture =
            new CompletableFuture<>();
    CompletableFuture<SendResult<String, CreateBidDto>> kafkaResultFuture =
            new CompletableFuture<>();
    Exception exception = new RuntimeException("Kafka send failure");

    when(bidKafkaTemplate.send(eq("Bids"), eq(bidDto))).thenReturn(kafkaResultFuture);
    kafkaResultFuture.completeExceptionally(exception);

    bidKafkaHelpers.attemptSend(bidDto, maxRetries, backoffMs, currentAttempt, resultFuture);

    verify(bidKafkaTemplate, times(1)).send(eq("Bids"), eq(bidDto));
  }

  @Test
  void testAttemptSendFailureMaxRetriesExceeded() {
    CreateBidDto bidDto = new CreateBidDto();
    int maxRetries = 0;
    long backoffMs = 100L;
    int currentAttempt = 0;
    CompletableFuture<SendResult<String, CreateBidDto>> resultFuture =
            new CompletableFuture<>();
    CompletableFuture<SendResult<String, CreateBidDto>> kafkaResultFuture =
            new CompletableFuture<>();
    Exception exception = new RuntimeException("Kafka send failure");

    when(bidKafkaTemplate.send(eq("Bids"), eq(bidDto))).thenReturn(kafkaResultFuture);
    kafkaResultFuture.completeExceptionally(exception);

    bidKafkaHelpers.attemptSend(bidDto, maxRetries, backoffMs, currentAttempt, resultFuture);

    verify(bidKafkaTemplate, times(1)).send(eq("Bids"), eq(bidDto));

    assert resultFuture.isDone();
    assert resultFuture.isCompletedExceptionally();
  }
}
