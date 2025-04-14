package backend.common.config.kafka;

import backend.common.config.logging.AppLogger;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

@Service
public class KafkaConfig {

  private final KafkaAdmin kafkaAdmin;

  @Value("${kafka.topics}")
  private String[] topics;

  @Autowired
  public KafkaConfig(KafkaAdmin kafkaAdmin) {
    this.kafkaAdmin = kafkaAdmin;
  }

  @PostConstruct
  public void initializeKafka() {
    int maxAttempts = 5;
    int attempt = 0;

    while (attempt < maxAttempts) {
      try {
        for (String topic : topics) {
          NewTopic newTopic = new NewTopic(topic, 1, (short) 1);
          kafkaAdmin.createOrModifyTopics(newTopic);
          AppLogger.info("Kafka topic '{}' created successfully.", newTopic);
        }
        return;
      } catch (Exception e) {
        AppLogger.error("Attempt to create Kafka topic failed: ", e);
        attempt++;
        try {
          Thread.sleep(5000);
        } catch (InterruptedException ie) {
          Thread.currentThread().interrupt();
          return;
        }
      }
    }
    AppLogger.error("Failed to create Kafka topic ");

  }
}

