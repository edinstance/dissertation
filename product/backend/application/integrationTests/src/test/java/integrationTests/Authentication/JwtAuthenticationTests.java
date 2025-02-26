package integrationTests.Authentication;

import integrationTests.Cognito.CognitoUtilities;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

public class JwtAuthenticationTests {

  private Response response;

  @When("the client requests the application health without a jwt token")
  public void theClientRequestsTheApplicationHealthWithoutAJwtToken() {
    response = RestAssured.given().get("/details/health");
  }

  @When("the client requests the application health with an invalid jwt token")
  public void theClientRequestsTheApplicationHealthWithAnInvalidJwtToken() {
    response = RestAssured.given().header("Authorization", "invalid-key").get("/details/health");
  }

  @Then("the server does not allow the request due to the token")
  public void theServerDoesNotAllowTheRequest() {
    response.then().statusCode(401);
  }

  @When("the client requests the application health with a valid jwt token")
  public void theClientRequestsTheApplicationHealthWithAValidJwtToken() {
    response = RestAssured.given().header("Authorization", "Bearer " + CognitoUtilities.getAccessToken()).get("/details/health");
  }

  @Then("the server allows the request and returns the health because of the token")
  public void theServerAllowsTheRequestAndReturnsTheHealth() {
    response.then().statusCode(200).body("status", equalTo("UP"));
  }
}
