package integrationTests.MutationTests;

import com.finalproject.backend.entities.UserDetailsEntity;
import io.cucumber.java.en.Given;
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

public class UserDetailsMutationTests {

  private Response response;

  private String userId;

  @Given("a user exists")
  public void aUserExists() {

    userId = UUID.randomUUID().toString();

    // Construct the GraphQL mutation query
    String mutation = String.format(
            "{ \"query\": \"mutation { createUser(userInput: { id: \\\"%s\\\", name: \\\"Test Name\\\", email: \\\"test@example.com\\\" }) { id name email status } }\" }",
            userId);

    // Send the mutation request to the /graphql endpoint
    response = given()
            .header("x-api-key", "test")
            .contentType("application/json")
            .body(mutation)
            .post("/graphql");
  }

  @When("the user saves their details")
  public void theUserSavesTheirDetails() {
    // Construct the GraphQL mutation query
    String mutation = String.format(
            "{ \"query\": \"mutation { saveUserDetails(id: \\\"%s\\\", detailsInput: { contactNumber: \\\"1234567890\\\", addressStreet: \\\"123 Test St\\\", addressCity: \\\"Test City\\\", addressCounty: \\\"Test County\\\", addressPostcode: \\\"12345\\\" }) { id name email status details { contactNumber addressStreet addressCity addressCounty addressPostcode } } }\" }",
            userId);

    // Send the mutation request to the /graphql endpoint
    response = given()
            .header("x-api-key", "test")
            .contentType("application/json")
            .body(mutation)
            .post("/graphql");
  }

  @Then("the server creates the user details in the database")
  public void theServerCreatesTheUserInTheDatabase() throws SQLException {
    // Checks the database to see if the user was created
    try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres",
            "password")) {
      String selectQuery = String.format("SELECT * FROM user_details WHERE user_id = '%s';", userId);
      Statement statement = connection.createStatement();
      boolean results = statement.execute(selectQuery);

      assert results;
    }
  }

  @Then("the server returns the user with the new details")
  public void theServerReturnsTheNewUser() {
    // Checks if the response is the same as the original user
    assert response.getStatusCode() == 200;
    assert response.getBody().jsonPath().getString("data.saveUserDetails.id").equals(userId);
    assert response.getBody().jsonPath().getString("data.saveUserDetails.name").equals("Test Name");
    assert response.getBody().jsonPath().getString("data.saveUserDetails.email").equals("test@example.com");
    assert response.getBody().jsonPath().getString("data.saveUserDetails.status").equals("PENDING");
    assertNotNull(response.getBody().jsonPath().getString("data.saveUserDetails.details"));

    UserDetailsEntity userDetails = response.getBody().jsonPath().getObject("data.saveUserDetails.details",
            UserDetailsEntity.class);
    assertNotNull(userDetails);
    assert userDetails.getContactNumber().equals("1234567890");
    assert userDetails.getAddressStreet().equals("123 Test St");
    assert userDetails.getAddressCity().equals("Test City");
    assert userDetails.getAddressCounty().equals("Test County");
    assert userDetails.getAddressPostcode().equals("12345");
  }

  @Given("a user with details exists")
  public void aUserWithDetailsExists() {

    userId = UUID.randomUUID().toString();

    // Construct the createUser GraphQL mutation query
    String mutation = String.format(
            "{ \"query\": \"mutation { createUser(userInput: { id: \\\"%s\\\", name: \\\"Test Name\\\", email: \\\"test2@example.com\\\" }) { id name email status } }\" }",
            userId);

    // Send the mutation request to the /graphql endpoint
    response = given()
            .header("x-api-key", "test")
            .contentType("application/json")
            .body(mutation)
            .post("/graphql");

    // Construct the saveUserDetails GraphQL mutation query
    mutation = String.format(
            "{ \"query\": \"mutation { saveUserDetails(id: \\\"%s\\\", detailsInput: { contactNumber: \\\"1234567890\\\", addressStreet: \\\"123 Test St\\\", addressCity: \\\"Test City\\\", addressCounty: \\\"Test County\\\", addressPostcode: \\\"12345\\\" }) { id details { contactNumber addressStreet addressCity addressCounty addressPostcode } } }\" }",
            userId);

    // Send the mutation request to the /graphql endpoint
    response = given()
            .header("x-api-key", "test")
            .contentType("application/json")
            .body(mutation)
            .post("/graphql");
  }

  @Then("the user updates their details")
  public void theUserUpdatesTheirDetails() {
    // Construct the GraphQL mutation query
    String mutation = String.format(
            "{ \"query\": \"mutation { saveUserDetails(id: \\\"%s\\\", detailsInput: { contactNumber: \\\"0\\\", addressStreet: \\\"Updated Street\\\", addressCity: \\\"Updated City\\\", addressCounty: \\\"Updated County\\\", addressPostcode: \\\"Updated Postcode\\\" }) { id details { contactNumber addressStreet addressCity addressCounty addressPostcode } } }\" }",
            userId);

    // Send the mutation request to the /graphql endpoint
    response = given()
            .header("x-api-key", "test")
            .contentType("application/json")
            .body(mutation)
            .post("/graphql");
  }

  @Then("the server returns the user with the updated details")
  public void theServerReturnsTheUserWithTheUpdatedDetails() {
    // Checks if the response is the same as the original user
    assert response.getStatusCode() == 200;
    assert response.getBody().jsonPath().getString("data.saveUserDetails.id").equals(userId);
    assertNotNull(response.getBody().jsonPath().getString("data.saveUserDetails.details"));

    // Check the details have been updated
    UserDetailsEntity userDetails = response.getBody().jsonPath().getObject("data.saveUserDetails.details",
            UserDetailsEntity.class);
    assertNotNull(userDetails);
    assert userDetails.getContactNumber().equals("0");
    assert userDetails.getAddressStreet().equals("Updated Street");
    assert userDetails.getAddressCity().equals("Updated City");
    assert userDetails.getAddressCounty().equals("Updated County");
    assert userDetails.getAddressPostcode().equals("Updated Postcode");
  }
}
