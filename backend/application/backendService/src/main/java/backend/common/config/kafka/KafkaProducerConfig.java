package backend.common.config.kafka;

import backend.bids.dto.CreateBidDto;
import backend.items.entities.ItemEntity;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * Configuration for the kafka producers.
 */
@Configuration
public class KafkaProducerConfig {

  private static final String CREATE_BID_DTO_TYPE = CreateBidDto.class.getName();
  private static final String ITEM_ENTITY_TYPE = ItemEntity.class.getName();


  /**
   * The kafka bootstrap address.
   */
  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapAddress;

  /**
   * Generic producer factory.
   *
   * @return the producer factory.
   */
  @Bean
  public ProducerFactory<String, Object> producerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    configProps.put(JsonSerializer.TYPE_MAPPINGS,
            CREATE_BID_DTO_TYPE + ":" + CREATE_BID_DTO_TYPE + ","
                    + ITEM_ENTITY_TYPE + ":" + ITEM_ENTITY_TYPE);
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  /**
   * Generic Kafka template bean.
   *
   * @return the kafka template
   */
  @Bean
  public KafkaTemplate<String, Object> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

}