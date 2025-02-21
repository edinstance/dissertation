package integrationTests.QueryTests;

import integrationTests.Cognito.CognitoUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class ItemQueryTests {

  private final SimpleDateFormat dateFormat =
          new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private final String formattedDate = dateFormat.format(new Date());
  private Response initalItemResponse;
  private Response result;
  private String itemId;

  @And("an item the user wants exists")
  public void anItemTheUserWantsExists() {
    String mutation = String.format("{ \"query\": \"mutation { saveItem(itemInput: { " +
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

    initalItemResponse = given()
            .header("Authorization", "Bearer " + CognitoUtilities.getAccessToken())
            .contentType("application/json")
            .body(mutation)
            .post("/graphql");

    itemId = initalItemResponse.getBody().jsonPath().getString("data.saveItem.id");
  }

  @When("a user searches for the item")
  public void aUserSearchesForTheItem() {
    String query = String.format("{ \"query\": \"query { searchForItems(searchText: \\\"%s\\\") { " +
                    "id " +
                    "name " +
                    "description " +
                    "isActive " +
                    "endingTime " +
                    "price " +
                    "stock " +
                    "category " +
                    "images " +
                    "seller { id name } " +
                    "} }\" }",
            "Test Item");  // Using the same item name from the Given step

    result = given()
            .contentType("application/json")
            .body(query)
            .post("/graphql");
  }

  @Then("the server returns the item the user searched for")
  public void theServerReturnsTheItemTheUserSearchedFor() {
    assert Objects.equals(result.getBody().jsonPath().getString("data.searchForItems[0].id"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.id"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.searchForItems[0].name"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.name"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.searchForItems[0].description"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.description"));
    assert Objects.equals(result.getBody().jsonPath().getBoolean("data.searchForItems[0].isActive"),
            initalItemResponse.getBody().jsonPath().getBoolean("data.saveItem.isActive"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.searchForItems[0].endingTime"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.endingTime"));
    assert Objects.equals(result.getBody().jsonPath().getDouble("data.searchForItems[0].price"),
            initalItemResponse.getBody().jsonPath().getDouble("data.saveItem.price"));
    assert Objects.equals(result.getBody().jsonPath().getInt("data.searchForItems[0].stock"),
            initalItemResponse.getBody().jsonPath().getInt("data.saveItem.stock"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.searchForItems[0].category"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.category"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.searchForItems[0].images"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.images"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.searchForItems[0].seller.id"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.seller.id"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.searchForItems[0].seller.name"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.seller.name"));
  }

  @When("the user searches for the item by its id")
  public void theUserSearchesForTheItemByItsId() {
    String query = String.format("{ \"query\": \"query { getItemById(id: \\\"%s\\\") { " +
                    "id " +
                    "name " +
                    "description " +
                    "isActive " +
                    "endingTime " +
                    "price " +
                    "stock " +
                    "category " +
                    "images " +
                    "seller { id name } " +
                    "} }\" }",
            itemId);

    result = given()
            .contentType("application/json")
            .body(query)
            .post("/graphql");
  }

  @Then("the server returns the item with the correct id")
  public void theServerReturnsTheItemWithTheCorrectId() {
    assert Objects.equals(result.getBody().jsonPath().getString("data.getItemById.id"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.id"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.getItemById.name"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.name"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.getItemById.description"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.description"));
    assert Objects.equals(result.getBody().jsonPath().getBoolean("data.getItemById.isActive"),
            initalItemResponse.getBody().jsonPath().getBoolean("data.saveItem.isActive"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.getItemById.endingTime"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.endingTime"));
    assert Objects.equals(result.getBody().jsonPath().getDouble("data.getItemById.price"),
            initalItemResponse.getBody().jsonPath().getDouble("data.saveItem.price"));
    assert Objects.equals(result.getBody().jsonPath().getInt("data.getItemById.stock"),
            initalItemResponse.getBody().jsonPath().getInt("data.saveItem.stock"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.getItemById.category"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.category"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.getItemById.images"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.images"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.getItemById.seller.id"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.seller.id"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.getItemById.seller.name"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.seller.name"));
  }
}