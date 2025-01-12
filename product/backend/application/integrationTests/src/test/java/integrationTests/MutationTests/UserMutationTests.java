package integrationTests.MutationTests;

import integrationTests.Cognito.CognitoUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserMutationTests {

  private Response response;

  private String userId;

  @When("the client sends a request to create a new user")
  public void theClientSendsARequestToCreateANewUser() {
    userId = UUID.randomUUID().toString();

    // Construct the GraphQL mutation query
    String mutation = String.format("{ \"query\": \"mutation { createUser(userInput: { id: \\\"%s\\\", name: \\\"Test Name\\\", email: \\\"test@example.com\\\" }) { id name email status } }\" }", userId);

    // Send the mutation request to the /graphql endpoint
    response = given()
            .header("x-api-key", "test")
            .contentType("application/json")
            .body(mutation)
            .post("/graphql");
  }

  @Then("the server creates the user in the database")
  public void theServerCreatesTheUserInTheDatabase() throws SQLException {
    // Checks the database to see if the user was created
    try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "password")) {
      String selectQuery = String.format("SELECT * FROM users WHERE user_id = '%s';", userId);
      Statement statement = connection.createStatement();
      boolean results = statement.execute(selectQuery);

      assert results;
    }
  }

  @And("the server returns the new user")
  public void theServerReturnsTheNewUser() {
    // Checks if the response is the same as the original user
    assert response.getStatusCode() == 200;
    assert response.getBody().jsonPath().getString("data.createUser.id").equals(userId);
    assert response.getBody().jsonPath().getString("data.createUser.name").equals("Test Name");
    assert response.getBody().jsonPath().getString("data.createUser.email").equals("test@example.com");
    assert response.getBody().jsonPath().getString("data.createUser.status").equals("PENDING");

  }


  @When("there is a user with details who wants to delete their account")
  public void thereIsAUserWhoWantsToDeleteTheirAccount() {
    userId = CognitoUtilities.getUserId();
    assertNotNull(userId);

    String mutation = String.format("{ \"query\": \"mutation { createUser(userInput: " +
            "{ id: \\\"%s\\\", name: \\\"Test Name\\\", email: \\\"test@example.com\\\" })" +
            " { id name email status } }\" }", userId);

    // Send the mutation request to the /graphql endpoint
    given()
            .header("x-api-key", "test")
            .contentType("application/json")
            .body(mutation)
            .post("/graphql");


    mutation = String.format(
            "{ \"query\": \"mutation { saveUserDetails(id: \\\"%s\\\", detailsInput: " +
                    "{ contactNumber: \\\"1234567890\\\", addressStreet: \\\"123 Test St\\\", " +
                    "addressCity: \\\"Test City\\\", addressCounty: \\\"Test County\\\", " +
                    "addressPostcode: \\\"12345\\\" }) { id name email status details " +
                    "{ contactNumber addressStreet addressCity addressCounty addressPostcode } } }\" }",
            userId);

    // Send the mutation request to the /graphql endpoint
    given()
            .header("x-api-key", "test")
            .contentType("application/json")
            .body(mutation)
            .post("/graphql");

  }

  @Then("the user sends a request to delete their account")
  public void theUserSendsARequestToDeleteTheirAccount() {

    String mutation = "{ \"query\": \"mutation { deleteUser { success message } }\" }";

    // Send the mutation request to the /graphql endpoint
    response = given()
            .header("Authorization", "Bearer "
                    + CognitoUtilities.getAccessToken())
            .contentType("application/json")
            .body(mutation)
            .post("/graphql");


    assert response.getStatusCode() == 200;
  }

  @And("the server returns a successful delete response")
  public void theServerReturnsASuccessfulDeleteResponse() {
    assert response.getStatusCode() == 200;
    assert response.getBody().jsonPath().getBoolean("data.deleteUser.success");
    assert response.getBody().jsonPath().getString("data.deleteUser.message").equals("User deleted successfully");
  }
}
