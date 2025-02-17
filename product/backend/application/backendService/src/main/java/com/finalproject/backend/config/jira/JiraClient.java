package com.finalproject.backend.config.jira;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.entities.UserEntity;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class creates a Jira Client.
 */
@Component
public class JiraClient {

  /**
   * The object mapper for converting objects to json.
   */
  private final ObjectMapper objectMapper;

  /**
   * The Http client for sending Http requests.
   */
  private final HttpClient httpClient = HttpClient.newHttpClient();

  /**
   * The url of the Jira account.
   */
  private final String baseUrl;

  /**
   * The authentication headers to use in http requests.
   */
  private final String authHeader;

  /**
   * The project key of the jira project.
   */
  private final String projectKey;

  /**
   * This constructor creates the Jira client.
   *
   * @param baseUrl the url of the Jira instance to interact with.
   * @param email the email associated to the account being used.
   * @param projectKey the key of the project which is being used.
   * @param accessToken the access token for the account being used.
   */
  public JiraClient(
          @Value("${jira.url}") String baseUrl,
          @Value("${jira.email}") String email,
          @Value("${jira.projectKey}") String projectKey,
          @Value("${jira.accessToken}") String accessToken
  ) {
    this.baseUrl = baseUrl;
    this.projectKey = projectKey;
    this.objectMapper = new ObjectMapper();
    this.authHeader = createAuthHeader(email, accessToken);
  }

  /**
   * This method creates the authentication header needed.
   *
   * @param email the email of the account to use.
   * @param accessToken the access token of the account to use.
   * @return a base64 authentication header.
   */
  public String createAuthHeader(final String email, final String accessToken) {
    String auth = email + ":" + accessToken;
    return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
  }

  /**
   * This method creates a bug ticket in Jira.
   *
   * @param title the ticket title.
   * @param user the user that logged the bug.
   * @param message the message the user added.
   */
  public void createBug(final String title, final UserEntity user, final String message) {
    try {
      String jsonPayload = createBugPayload(title, user, message);

      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(baseUrl + "/rest/api/2/issue"))
              .header("Authorization", authHeader)
              .header("Content-Type", "application/json")
              .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
              .build();

      HttpResponse<String> response = httpClient.send(request,
              HttpResponse.BodyHandlers.ofString());

      System.out.println(response.body());

    } catch (Exception e) {
      System.out.println("Error creating bug: " + e.getMessage());
    }
  }

  /**
   * This method creates the body of the http request for creating a bug.
   *
   * @param title the title of the ticket.
   * @param user the user that reported the bug.
   * @param message the message the user added.
   * @return json value of the information.
   * @throws JsonProcessingException if there is a json processing error.
   */
  private String createBugPayload(final String title, final UserEntity user,
                                  final String message) throws JsonProcessingException {
    Map<String, Object> fields = new HashMap<>();
    fields.put("project", Map.of("key", projectKey));
    fields.put("summary", title);
    fields.put("description", "User ID" + user.getId() + "\n User Email "
            + user.getEmail() + "\n Message: " + message);
    fields.put("issuetype", Map.of("name", "Bug"));

    Map<String, Object> payload = new HashMap<>();
    payload.put("fields", fields);

    return objectMapper.writeValueAsString(payload);
  }

}
