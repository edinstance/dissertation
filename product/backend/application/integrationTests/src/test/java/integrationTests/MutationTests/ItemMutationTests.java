package integrationTests.MutationTests;

import integrationTests.Cognito.CognitoUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;

public class ItemMutationTests {

  private Response response;
  private final SimpleDateFormat dateFormat =
          new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private final String formattedDate = dateFormat.format(new Date());

  private String mutation;

  private String itemId;


  @When("the user sends a request to create an item")
  public void theUserSendsARequestToCreateAnItem() {

    mutation = String.format("{ \"query\": \"mutation { saveItem(itemInput: { " +
                    "name: \\\"%s\\\", " +
                    "description: \\\"%s\\\", " +
                    "isActive: %b, " +
                    "endingTime: \\\"%s\\\", " +
                    "price: %f, " +
                    "stock: %d, " +
                    "category: \\\"%s\\\", " +
                    "images: [%s] " +
                    "}) { " +
                    "id name description isActive endingTime price stock category images " +
                    "seller { id name } " +
                    "} }\" }",
            "Test Item",
            "This is a test item",
            true,
            formattedDate,
            99.99,
            10,
            "Electronics",
            "\\\"/images/test.jpg\\\"");

    response = given()
            .header("Authorization", "Bearer " + CognitoUtilities.getAccessToken())
            .contentType("application/json")
            .body(mutation)
            .post("/graphql");
  }

  @Then("the server returns the correct item")
  public void theServerReturnsTheCorrectItem() {

    assert response.getStatusCode() == 200;
    assert response.getBody().jsonPath().getString("data.saveItem.id") != null;
    assert response.getBody().jsonPath().getString("data.saveItem.name").equals("Test Item");
    assert response.getBody().jsonPath().getString("data.saveItem.description").equals("This is a test item");
    assert response.getBody().jsonPath().getBoolean("data.saveItem.isActive");
    assert response.getBody().jsonPath().getString("data.saveItem.endingTime") != null;
    assert new BigDecimal(response.getBody().jsonPath().getString("data.saveItem.price"))
            .equals(new BigDecimal("99.99"));
    assert response.getBody().jsonPath().getInt("data.saveItem.stock") == 10;
    assert response.getBody().jsonPath().getString("data.saveItem.category").equals("Electronics");
    assert response.getBody().jsonPath().getList("data.saveItem.images").get(0).equals("/images/test.jpg");
    assert response.getBody().jsonPath().getString("data.saveItem.seller.id") != null;
    assert response.getBody().jsonPath().getString("data.saveItem.seller.name") != null;
  }

  @And("an item exists")
  public void anItemExists() {

    mutation = String.format("{ \"query\": \"mutation { saveItem(itemInput: { " +
                    "name: \\\"%s\\\", " +
                    "description: \\\"%s\\\", " +
                    "isActive: %b, " +
                    "endingTime: \\\"%s\\\", " +
                    "price: %f, " +
                    "stock: %d, " +
                    "category: \\\"%s\\\", " +
                    "images: [%s] " +
                    "}) { " +
                    "id name description isActive endingTime price stock category images " +
                    "seller { id name } " +
                    "} }\" }",
            "Test Item",
            "This is a test item",
            true,
            formattedDate,
            99.99,
            10,
            "Electronics",
            "\\\"/images/test.jpg\\\"");

    response = given()
            .header("Authorization", "Bearer " + CognitoUtilities.getAccessToken())
            .contentType("application/json")
            .body(mutation)
            .post("/graphql");
    itemId = response.getBody().jsonPath().getString("data.saveItem.id");
  }

  @When("the user updates the item")
  public void theUserUpdatesTheItem() {
    mutation = String.format("{ \"query\": \"mutation { saveItem(itemInput: { " +
                    "id: \\\"%s\\\", " +
                    "name: \\\"%s\\\", " +
                    "description: \\\"%s\\\", " +
                    "isActive: %b, " +
                    "endingTime: \\\"%s\\\", " +
                    "price: %f, " +
                    "stock: %d, " +
                    "category: \\\"%s\\\", " +
                    "images: [%s] " +
                    "}) { id name description isActive endingTime price stock category images } }\" }",
            itemId,
            "Updated Item",
            "This is an updated item",
            true,
            formattedDate,
            11.11,
            15,
            "Electronics",
            "\\\"/images/test.jpg\\\"");

    response = given()
            .header("Authorization", "Bearer " + CognitoUtilities.getAccessToken())
            .contentType("application/json")
            .body(mutation)
            .post("/graphql");
  }

  @Then("the server returns the updated item")
  public void theServerReturnsTheUpdatedItem() {
    assert response.getStatusCode() == 200;
    assert response.getBody().jsonPath().getString("data.saveItem.id").equals(itemId);
    assert response.getBody().jsonPath().getString("data.saveItem.name").equals("Updated Item");
    assert response.getBody().jsonPath().getString("data.saveItem.description").equals("This is an updated item");
    assert response.getBody().jsonPath().getBoolean("data.saveItem.isActive");
    assert response.getBody().jsonPath().getString("data.saveItem.endingTime") != null;
    assert new BigDecimal(response.getBody().jsonPath().getString("data.saveItem.price"))
            .equals(new BigDecimal("11.11"));
    assert response.getBody().jsonPath().getInt("data.saveItem.stock") == 15;
    assert response.getBody().jsonPath().getString("data.saveItem.category").equals("Electronics");
    assert response.getBody().jsonPath().getList("data.saveItem.images").get(0).equals("/images/test.jpg");
  }
}
