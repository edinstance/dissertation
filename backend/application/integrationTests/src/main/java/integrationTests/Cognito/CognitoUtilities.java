package integrationTests.Cognito;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.Map;

@Component
public class CognitoUtilities {

  private static String accessToken;
  private static String adminAccessToken;
  private static String userId;
  private final String userPoolId = System.getProperty("TEST_AWS_USER_POOL_ID");
  private final CognitoIdentityProviderClient cognitoClient = CognitoClient.getInstance();

  public static String getAccessToken() {
    return accessToken;
  }

  public static String getAdminAccessToken() {
    return adminAccessToken;
  }

  public static String getUserId() {
    return userId;
  }

  public void createUser(String username, String password, Boolean isAdmin) {

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

    AdminGetUserRequest getUserRequest = AdminGetUserRequest.builder()
            .userPoolId(userPoolId)
            .username(username)
            .build();

    AdminGetUserResponse response = cognitoClient.adminGetUser(getUserRequest);

    if(!isAdmin) {
      userId = response.userAttributes().stream()
              .filter(attr -> "sub".equals(attr.name()))
              .findFirst()
              .map(AttributeType::value)
              .orElseThrow(() -> new RuntimeException("User ID not found"));
    }

  }

  public void deleteUser(String username) {
    AdminDeleteUserRequest userRequest = AdminDeleteUserRequest.builder()
            .userPoolId(userPoolId)
            .username(username).build();

    cognitoClient.adminDeleteUser(userRequest);

  }

  public void makeUserAdmin(String username) {

    AdminAddUserToGroupRequest addRequest =
            AdminAddUserToGroupRequest.builder()
                    .userPoolId(userPoolId)
                    .username(username)
                    .groupName("SubShopAdmin")
                    .build();

    cognitoClient.adminAddUserToGroup(addRequest);

  }

  public void loginUser(String username, String password, Boolean isAdmin) {

    Map<String, String> authParameters = new java.util.HashMap<>();
    authParameters.put("USERNAME", username);
    authParameters.put("PASSWORD", password);

    InitiateAuthRequest loginRequest = InitiateAuthRequest.builder()
            .clientId(System.getProperty("TEST_AWS_CLIENT_ID"))
            .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
            .authParameters(authParameters)
            .build();

    InitiateAuthResponse response = cognitoClient.initiateAuth(loginRequest);

    if (isAdmin) {
      adminAccessToken = response.authenticationResult().accessToken();
    } else {

      accessToken = response.authenticationResult().accessToken();
    }

  }
}

