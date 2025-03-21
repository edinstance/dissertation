package integrationTests.QueryTests;

import integrationTests.Cognito.CognitoUtilities;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class AdminQueryTests {

  String getUserStatsQuery = "{ \"query\": \"query { getUserStats { total newUserTotal deletedUserTotal } }\" }";


  private Response result;

  @When("a user tries to access admin data")
  public void aUserTriesToAccessAdminData() {
    result = given()
            .contentType("application/json")
            .body(getUserStatsQuery)
            .post("/graphql");
  }


  @Then("the server denies their request as they are not an admin")
  public void theServerDeniesTheirRequestAsTheyAreNotAnAdmin() {
    assert result.getBody().jsonPath().getString("errors[0].message").equals("Access denied");
  }

  @When("the admin requests the user stats")
  public void theAdminRequestsTheUserStats() {

    assert CognitoUtilities.getAdminAccessToken() != null;
    result = given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + CognitoUtilities.getAdminAccessToken())
            .body(getUserStatsQuery)
            .post("/graphql");
  }

  @Then("the server returns the user stats")
  public void theServerReturnsTheUserStats() {
    assert result.getBody().jsonPath().getString("data.getUserStats.total").equals("1");
    assert result.getBody().jsonPath().getString("data.getUserStats.newUserTotal").equals("1");
    assert result.getBody().jsonPath().getString("data.getUserStats.deletedUserTotal").equals("0");
  }
}
