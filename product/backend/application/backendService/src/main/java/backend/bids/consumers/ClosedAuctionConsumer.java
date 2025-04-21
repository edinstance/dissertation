package backend.bids.consumers;

import static backend.common.config.kafka.KafkaGroups.CLOSED_AUCTION_GROUP;
import static backend.common.config.kafka.KafkaTopics.CLOSED_AUCTION_TOPIC;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ClosedAuctionConsumer {

  @KafkaListener(topics = CLOSED_AUCTION_TOPIC, groupId = CLOSED_AUCTION_GROUP)
  public void listen(String message) {
    System.out.println("Received message: " + message);
  }
}
