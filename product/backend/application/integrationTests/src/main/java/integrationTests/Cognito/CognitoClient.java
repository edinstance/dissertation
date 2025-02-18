package integrationTests.Cognito;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

public class CognitoClient {

  private static volatile CognitoIdentityProviderClient client;

  private CognitoClient() {

  }

  public static CognitoIdentityProviderClient getInstance() {
    if (client == null) {
      synchronized (CognitoClient.class) {
        if (client == null) {
          client = CognitoIdentityProviderClient.builder()
                  .region(Region.of(System.getProperty("TEST_AWS_REGION")))
                  .build();
        }
      }
    }
    return client;
  }
}
