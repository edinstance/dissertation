package integrationTests.Jira;


import io.restassured.RestAssured;
import io.restassured.response.Response;

public class JiraUtilities {

  private final String jiraEmail = System.getProperty("JIRA_EMAIL");
  private final String jiraAccessToken = System.getProperty("JIRA_ACCESS_TOKEN");
  private final String jiraUrl = System.getProperty("JIRA_URL");

  private String getJiraAuthHeader() {
    String auth = jiraEmail + ":" + jiraAccessToken;
    return java.util.Base64.getEncoder().encodeToString(auth.getBytes());
  }

  public Response searchJiraIssues(String query) {
    return RestAssured.given()
            .header("Authorization", "Basic " + getJiraAuthHeader())
            .contentType("application/json")
            .queryParam("jql", query)
            .get(jiraUrl + "/rest/api/2/search");
  }

  public void deleteJiraIssue(String issueKey) {
    RestAssured.given()
            .header("Authorization", "Basic " + getJiraAuthHeader())
            .delete(jiraUrl + "/rest/api/2/issue/" + issueKey);
  }
}
