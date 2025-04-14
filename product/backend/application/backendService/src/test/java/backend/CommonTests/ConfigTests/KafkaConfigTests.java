package backend.CommonTests.ConfigTests;

import backend.common.config.kafka.KafkaConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.test.util.ReflectionTestUtils;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class KafkaConfigTests {

 @Mock
 private KafkaAdmin kafkaAdmin;

 @InjectMocks
 private KafkaConfig kafkaConfig;

 @Test
 public void testInitializeKafka_Success() {
   String[] topics = {"test-topic1", "test-topic2"};
   ReflectionTestUtils.setField(kafkaConfig, "topics", topics);

   kafkaConfig.initializeKafka();

   verify(kafkaAdmin, times(2)).createOrModifyTopics(any(NewTopic.class));
 }

 @Test
 public void testInitializeKafka_TopicCreationFailure() throws InterruptedException {
   String[] topics = {"test-topic1"};
   ReflectionTestUtils.setField(kafkaConfig, "topics", topics);

   doThrow(new RuntimeException("Topic creation failed"))
           .when(kafkaAdmin)
           .createOrModifyTopics(any(NewTopic.class));

   kafkaConfig.initializeKafka();

   verify(kafkaAdmin, times(5)).createOrModifyTopics(any(NewTopic.class));
 }

}
