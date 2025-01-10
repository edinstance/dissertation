package integrationTests.QueryTests;

import integrationTests.Cognito.CognitoUtilities;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserQueryTests {

  private Response response;

  @Given("a user is created using the cognito user")
  public void aCognitoUserExists() {
    String userId = CognitoUtilities.getUserId();
    assertNotNull(userId);

    String mutation = String.format("{ \"query\": \"mutation { createUser(userInput: " +
            "{ id: \\\"%s\\\", name: \\\"Test Name\\\", email: \\\"test@example.com\\\" })" +
            " { id name email status } }\" }", userId);

    // Send the mutation request to the /graphql endpoint
    response = given()
            .header("x-api-key", "test")
            .contentType("application/json")
            .body(mutation)
            .post("/graphql");

    assert response.getStatusCode() == 200;
  }

  @When("the user is queried")
  public void theUserIsQueried() {
    String query =
            "{ \"query\": \"query { getUser { id name email status details " +
                    "{ id contactNumber addressStreet addressCity " +
                    "addressCounty addressPostcode } } }\" }";

    response = given()
            .header("Authorization", "Bearer " + CognitoUtilities.getAccessToken())
            .contentType("application/json")
            .body(query)
            .post("/graphql");
  }

  @Then("the server returns the user details")
  public void theServerReturnsTheUserDetails() {
    assert response.getStatusCode() == 200;
    assert response.getBody().jsonPath().getString("data.getUser.id").equals(CognitoUtilities.getUserId());
    assert response.getBody().jsonPath().getString("data.getUser.name").equals("Test Name");
    assert response.getBody().jsonPath().getString("data.getUser.email").equals("test@example.com");
    assert response.getBody().jsonPath().getString("data.getUser.status").equals("PENDING");
  }
}
