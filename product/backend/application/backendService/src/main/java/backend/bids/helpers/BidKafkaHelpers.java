package backend.bids.helpers;

import backend.bids.dto.CreateBidDto;
import backend.common.config.logging.AppLogger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

/**
 * Kafka helpers for the bids.
 */
@Component
public class BidKafkaHelpers {

  /**
   * The bid topic.
   */
  private static final String BIDS_TOPIC = "Bids";

  /**
   * The kafka template for the bids.
   */
  private final KafkaTemplate<String, CreateBidDto> bidKafkaTemplate;

  /**
   * constructor for this helper.
   *
   * @param bidKafkaTemplate the kafka template.
   */
  @Autowired
  public BidKafkaHelpers(KafkaTemplate<String, CreateBidDto> bidKafkaTemplate) {
    this.bidKafkaTemplate = bidKafkaTemplate;
  }

  /**
   * A function that attempts to send a bitDto to kafka.
   *
   * @param bidDto the bid to send.
   * @param maxRetries the max number of retries.
   * @param backoffMs the time it should backoff for.
   * @param currentAttempt the current attempt
   * @param resultFuture the future of the result.
   */
  public void attemptSend(
          CreateBidDto bidDto,
          int maxRetries,
          long backoffMs,
          int currentAttempt,
          CompletableFuture<SendResult<String, CreateBidDto>> resultFuture) {

    bidKafkaTemplate.send(BIDS_TOPIC, bidDto)
            .whenComplete((result, ex) -> {
              if (ex == null) {
                AppLogger.info(
                        "Sent bid={} with offset=[{}] on attempt {}",
                        bidDto,
                        result.getRecordMetadata().offset(),
                        currentAttempt + 1);
                resultFuture.complete(result);
              } else {
                // Failure case
                AppLogger.warn("Failed to send bid to Kafka on attempt {}: {}",
                        currentAttempt + 1, ex.getMessage());

                if (currentAttempt < maxRetries) {
                  // Schedule retry with exponential backoff
                  long nextBackoff = backoffMs * 2; // Exponential backoff
                  AppLogger.info("Retrying in {} ms (attempt {}/{})",
                          backoffMs, currentAttempt + 2, maxRetries + 1);

                  CompletableFuture.delayedExecutor(backoffMs, TimeUnit.MILLISECONDS)
                          .execute(() -> attemptSend(
                                  bidDto, maxRetries, nextBackoff,
                                  currentAttempt + 1, resultFuture));
                } else {
                  // Max retries exceeded
                  AppLogger.error("Max retries ({}) exceeded for bid {}", maxRetries + 1, bidDto);
                  resultFuture.completeExceptionally(ex);
                }
              }
            });
  }
}
