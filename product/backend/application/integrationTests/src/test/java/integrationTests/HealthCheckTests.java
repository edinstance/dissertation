package integrationTests;

import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

public class HealthCheckTests {

    private Response response;

    @Then("the client requests the application health")
    public void theClientRequestsTheApplicationHealth() {
        response = RestAssured.given().header("x-api-key", "test").get("/details/health");
        // The api key is set as a hardcoded value as this should only be used on a test environment
    }

    @Then("the server returns the health status")
    public void theServerReturnsTheStatus() {
        response.then().statusCode(200)
                .body("status", equalTo("UP"));
    }
}