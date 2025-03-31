package integrationTests.QueryTests;

import integrationTests.Cognito.CognitoUtilities;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdminQueryTests {

  String getUserStatsQuery = "{ \"query\": \"query { getUserStats { total newUserTotal deletedUserTotal } }\" }";
  String getAllUsersQuery = "{ \"query\": \"query { getAllUsers { id email name } }\" }";


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

  @When("a user tries to access all the user data")
  public void aUserTriesToAccessAllTheUserData() {
    result = given()
            .contentType("application/json")
            .body(getAllUsersQuery)
            .post("/graphql");
  }

  @When("an admin tries to access all the user data")
  public void anAdminTriesToAccessAllTheUserData() {
    result = given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + CognitoUtilities.getAdminAccessToken())
            .body(getAllUsersQuery)
            .post("/graphql");
  }

  @Then("the server returns all the user data")
  public void theServerReturnsAllTheUserData() {
   assertNotNull( result.getBody().jsonPath().getString("data.getAllUsers[0]"));

  }
}
