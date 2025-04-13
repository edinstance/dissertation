package backend.common.dynamodb;

import java.net.URI;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;

/**
 * Configuration class for setting up the DynamoDB client and enhanced client.
 */
@Configuration
public class DynamoDbConfig {

  /**
   * The endpoint URL for DynamoDB, defaulting to localhost if not specified.
   */
  @Value("${aws.dynamodb.endpoint:http://localhost:8000}")
  private Optional<String> dynamodbEndpointUrl;

  /**
   * Creates a DynamoDB client.
   *
   * @return the DynamoDB client.
   */
  @Bean
  public DynamoDbClient dynamoDbClient() {
    DynamoDbClientBuilder builder = DynamoDbClient.builder()
            .region(Region.EU_WEST_2)
            .credentialsProvider(DefaultCredentialsProvider.create());

    dynamodbEndpointUrl.ifPresent(endpoint ->
            builder.endpointOverride(URI.create(endpoint))
    );

    return builder.build();
  }

  /**
   * Creates a DynamoDB enhanced client.
   *
   * @param dynamoDbClient the default DynamoDB client.
   * @return the DynamoDB enhanced client.
   */
  @Bean
  public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
    return DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build();
  }
}
