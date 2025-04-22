package backend.bids.helpers;


import backend.common.config.logging.AppLogger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

/**
 * Generic Kafka helpers.
 */
@Component
public class BidKafkaHelpers {

  /**
   * The kafka template.  No longer specific to CreateBidDto
   */
  private final KafkaTemplate<String, Object> kafkaTemplate;

  /**
   * constructor for this helper.
   *
   * @param kafkaTemplate the kafka template.
   */
  @Autowired
  public BidKafkaHelpers(KafkaTemplate<String, Object> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  /**
   * A function that attempts to send a message to kafka with retry logic.
   *
   * @param topic          the kafka topic.
   * @param key            the key.
   * @param message        the message to send.
   * @param maxRetries     the max number of retries.
   * @param backoffMs      the time it should backoff for.
   * @param currentAttempt the current attempt
   * @param resultFuture   the future of the result.
   */
  public <T> void attemptSend(
          String topic,
          String key,
          T message,
          int maxRetries,
          long backoffMs,
          int currentAttempt,
          CompletableFuture<SendResult<String, Object>> resultFuture) {
    kafkaTemplate.send(topic, key, message)
            .whenComplete(
                    (result, ex) -> {
                      if (ex == null) {
                        AppLogger.info(
                                "Sent message={} with offset=[{}] on attempt {}",
                                message,
                                result.getRecordMetadata().offset(),
                                currentAttempt + 1);
                        resultFuture.complete(result);
                      } else {
                        // Failure case
                        AppLogger.warn(
                                "Failed to send message to Kafka on attempt {}: {}",
                                currentAttempt + 1,
                                ex.getMessage());

                        if (currentAttempt < maxRetries) {
                          // Schedule retry with exponential backoff
                          long nextBackoff = backoffMs * 2; // Exponential backoff
                          AppLogger.info(
                                  "Retrying in {} ms (attempt {}/{})",
                                  backoffMs,
                                  currentAttempt + 2,
                                  maxRetries + 1);

                          CompletableFuture.delayedExecutor(backoffMs, TimeUnit.MILLISECONDS)
                                  .execute(
                                          () ->
                                                  attemptSend(
                                                          topic,
                                                          key,
                                                          message,
                                                          maxRetries,
                                                          nextBackoff,
                                                          currentAttempt + 1,
                                                          resultFuture));
                        } else {
                          // Max retries exceeded
                          AppLogger.error(
                                  "Max retries ({}) exceeded for message {}",
                                  maxRetries + 1, message);

                          resultFuture.completeExceptionally(ex);
                        }
                      }
                    });
  }
}
