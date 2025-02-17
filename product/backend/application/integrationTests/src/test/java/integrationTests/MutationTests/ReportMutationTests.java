package integrationTests.MutationTests;

import integrationTests.Cognito.CognitoUtilities;
import integrationTests.Jira.JiraUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportMutationTests {

  private final String title = UUID.randomUUID().toString();
  private final String description = "description";
  private final JiraUtilities jiraUtilities = new JiraUtilities();
  private Response response;
  private Response jiraResponse;

  @When("the user reports a bug")
  public void theUserReportsABug() {
    String mutation = String.format("{ \"query\": \"mutation { " +
                    "reportBug(title: \\\"%s\\\", description: \\\"%s\\\") { " +
                    "success message " +
                    "} }\" }",
            title,
            description);

    response = given()
            .header("Authorization", "Bearer " + CognitoUtilities.getAccessToken())
            .contentType("application/json")
            .body(mutation)
            .post("/graphql");
  }

  @Then("the bug is created in jira")
  public void theBugIsCreatedInJira() {
    String jql = String.format("summary ~ \"%s\" AND description ~ \"%s\" AND created >= -1m",
            title, description);

    jiraResponse = jiraUtilities.searchJiraIssues(jql);

    assertEquals(200, jiraResponse.getStatusCode());
    assertTrue(jiraResponse.getBody().jsonPath().getInt("total") > 0,
            "No matching issue found in Jira");

    assertEquals(title,
            jiraResponse.getBody().jsonPath().getString("issues[0].fields.summary"));
    assertTrue(jiraResponse.getBody().jsonPath().getString("issues[0].fields.description")
            .contains(description));
  }

  @And("the bug response is correct")
  public void theBugResponseIsCorrect() {
    assertEquals(200, response.getStatusCode());
    assertTrue(response.getBody().jsonPath().getBoolean("data.reportBug.success"));
    assertEquals(title + ": reported successfully", response.getBody().jsonPath().getString("data.reportBug.message"));
  }

  @And("the jira ticket is deleted")
  public void theJiraTicketIsDeleted() {
    String issueKey = jiraResponse.getBody().jsonPath().getString("issues[0].key");
    jiraUtilities.deleteJiraIssue(issueKey);
  }
}
