package backend.bids.consumers;

import static backend.common.config.kafka.KafkaGroups.BID_GROUP;
import static backend.common.config.kafka.KafkaTopics.BID_TOPIC;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BidConsumer {

  @KafkaListener(topics = BID_TOPIC, groupId = BID_GROUP)
  public void listen(String message) {
    System.out.println("Received message: " + message);
  }
}