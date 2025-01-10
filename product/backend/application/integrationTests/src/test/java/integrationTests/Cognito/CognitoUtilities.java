package integrationTests.Cognito;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.Map;

@Component
public class CognitoUtilities {

  private final String userPoolId = System.getProperty("TEST_AWS_USER_POOL_ID");

  private final CognitoIdentityProviderClient cognitoClient = CognitoClient.getInstance();

  private String accessToken;

  public void createUser(String username, String password) {

    AdminCreateUserRequest userRequest = AdminCreateUserRequest.builder()
            .userPoolId(userPoolId)
            .username(username)
            .temporaryPassword(password)
            .messageAction(MessageActionType.SUPPRESS)
            .build();

    cognitoClient.adminCreateUser(userRequest);

    AdminSetUserPasswordRequest passwordRequest = AdminSetUserPasswordRequest.builder()
            .userPoolId(userPoolId)
            .username(username)
            .password(password)
            .permanent(true)
            .build();

    cognitoClient.adminSetUserPassword(passwordRequest);

  }

  public void deleteUser(String username) {
    AdminDeleteUserRequest userRequest = AdminDeleteUserRequest.builder()
            .userPoolId(userPoolId)
            .username(username).build();

    cognitoClient.adminDeleteUser(userRequest);

  }

  public void loginUser(String username, String password) {

    Map<String, String> authParameters = new java.util.HashMap<>();
    authParameters.put("USERNAME", username);
    authParameters.put("PASSWORD", password);

    InitiateAuthRequest loginRequest = InitiateAuthRequest.builder()
            .clientId(System.getProperty("TEST_AWS_CLIENT_ID"))
            .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
            .authParameters(authParameters)
            .build();

    InitiateAuthResponse response = cognitoClient.initiateAuth(loginRequest);
    accessToken = response.authenticationResult().accessToken();
  }

}
