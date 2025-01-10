package integrationTests.Authentication;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

public class ApiKeyAuthenticationTests {

    private Response response;

    @When("the client requests the application health without an api key")
    public void theClientRequestsTheApplicationHealthWithoutAnApiKey() {
        response = RestAssured.given().get("/details/health");
    }

    @When("the client requests the application health with an invalid api key")
    public void theClientRequestsTheApplicationHealthWithAnInvalidApiKey() {
        response = RestAssured.given().header("x-api-key", "invalid-key").get("/details/health");
    }

    @Then("the server does not allow the request")
    public void theServerDoesNotAllowTheRequest() {
        response.then().statusCode(401);
    }

    @When("the client requests the application health with a valid api key")
    public void theClientRequestsTheApplicationHealthWithAValidApiKey() {
        response = RestAssured.given().header("x-api-key", "test").get("/details/health");
        // The api key is set as a hardcoded value as this should only be used on a test environment
    }

    @Then("the server allows the request and returns the health")
    public void theServerAllowsTheRequestAndReturnsTheHealth() {
        response.then().statusCode(200).body("status", equalTo("UP"));
    }
}
