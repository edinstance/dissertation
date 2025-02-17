package com.finalproject.backend.config.jira;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.entities.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public abstract class JiraClient {

  private final ObjectMapper objectMapper;
  private final HttpClient httpClient = HttpClient.newHttpClient();
  private final String baseUrl;
  private final String authHeader;
  private final String projectKey;

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

  public String createAuthHeader(final String email, final String accessToken) {
    String auth = email + ":" + accessToken;
    return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
  }

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

    } catch (Exception e) {
      System.out.println("Error creating bug: " + e.getMessage());
    }
  }

  private String createBugPayload(final String title, final UserEntity user, final String message) throws Exception {
    Map<String, Object> fields = new HashMap<>();
    fields.put("project", Map.of("key", projectKey));
    fields.put("summary", title);
    fields.put("description", "User ID" + user.getId() + "\n User Email " + user.getEmail() +"\n Message: " + message);
    fields.put("issuetype", Map.of("name", "Bug"));

    Map<String, Object> payload = new HashMap<>();
    payload.put("fields", fields);

    return objectMapper.writeValueAsString(payload);
  }

}
