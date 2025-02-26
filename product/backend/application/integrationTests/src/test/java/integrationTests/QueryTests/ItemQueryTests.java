package integrationTests.QueryTests;

import groovy.util.logging.Slf4j;
import integrationTests.Cognito.CognitoUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
public class ItemQueryTests {

  private static final Logger log = LoggerFactory.getLogger(ItemQueryTests.class);
  private final SimpleDateFormat dateFormat =
          new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private final String formattedDate = dateFormat.format(new Date());
  private Response initalItemResponse;
  private Response result;
  private String itemId;

  private final String mutation = String.format("{ \"query\": \"mutation { saveItem(itemInput: { " +
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

  @And("an item the user wants exists")
  public void anItemTheUserWantsExists() {

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
                    "items { " +
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
                    "} } }\" }",
            "Test Item");  // Using the same item name from the Given step

    result = given()
            .contentType("application/json")
            .body(query)
            .post("/graphql");
  }

  @Then("the server returns the item the user searched for")
  public void theServerReturnsTheItemTheUserSearchedFor() {
    assert Objects.equals(result.getBody().jsonPath().getString("data.searchForItems.items[0].id"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.id"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.searchForItems.items[0].name"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.name"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.searchForItems.items[0].description"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.description"));
    assert Objects.equals(result.getBody().jsonPath().getBoolean("data.searchForItems.items[0].isActive"),
            initalItemResponse.getBody().jsonPath().getBoolean("data.saveItem.isActive"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.searchForItems.items[0].endingTime"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.endingTime"));
    assert Objects.equals(result.getBody().jsonPath().getDouble("data.searchForItems.items[0].price"),
            initalItemResponse.getBody().jsonPath().getDouble("data.saveItem.price"));
    assert Objects.equals(result.getBody().jsonPath().getInt("data.searchForItems.items[0].stock"),
            initalItemResponse.getBody().jsonPath().getInt("data.saveItem.stock"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.searchForItems.items[0].category"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.category"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.searchForItems.items[0].images"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.images"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.searchForItems.items[0].seller.id"),
            initalItemResponse.getBody().jsonPath().getString("data.saveItem.seller.id"));
    assert Objects.equals(result.getBody().jsonPath().getString("data.searchForItems.items[0].seller.name"),
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

  @And("there are many items in the application")
  public void thereAreManyItemsInTheApplication() {
    for (int i = 0; i < 16; i++) {
      initalItemResponse = given()
              .header("Authorization", "Bearer " + CognitoUtilities.getAccessToken())
              .contentType("application/json")
              .body(mutation)
              .post("/graphql");
    }
  }

  @When("the user requests the items without pagination")
  public void theUserRequestsTheItemsWithoutPagination() {
    String query = String.format("{ \"query\": \"query { searchForItems(searchText: \\\"%s\\\") { " +
                    "items { " +
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
                    "} } }\" }",
            "Test Item");

    result = given()
            .contentType("application/json")
            .body(query)
            .post("/graphql");
  }

  @Then("the server returns paginated data")
  public void theServerReturnsPaginatedData() {
    List<Map<String, Object>> items = result.getBody().jsonPath()
            .getList("data.searchForItems.items");

    assertNotNull(items);
    assertFalse(items.isEmpty());
    assertTrue(items.size() <= 10);
  }

  @Then("the user requests for the second page of data")
  public void theUserRequestsForTheSecondPageOfData() {
    String query = String.format("{ \"query\": \"query { searchForItems(searchText: \\\"%s\\\"," +
                    "pagination: {page: 1, size: 10}) { " +
                    "items { " +
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
                    "} } }\" }",
            "Test Item");

    result = given()
            .contentType("application/json")
            .body(query)
            .post("/graphql");
  }

  @And("the server returns the second page of items")
  public void theServerReturnsTheSecondPageOfItems() {
    List<Map<String, Object>> items = result.getBody().jsonPath()
            .getList("data.searchForItems.items");

    assertNotNull(items);
    assertFalse(items.isEmpty());
    assertEquals(6, items.size());
  }

  @When("the user requests the users items")
  public void theUserRequestsTheUsersItems() {

    String query = String.format("{ \"query\": \"query { getItemsByUser(id: \\\"%s\\\"," +
                    "isActive: true," +
                    "pagination: {page: 1, size: 10}) { " +
                    "items { " +
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
                    "} } }\" }",
            CognitoUtilities.getUserId());

    result = given()
            .contentType("application/json")
            .body(query)
            .post("/graphql");
    
  }

  @Then("the server returns the users items")
  public void theServerReturnsTheUsersItems() {
    List<Map<String, Object>> items = result.getBody().jsonPath()
            .getList("data.getItemsByUser.items");
    log.info(String.valueOf(items));

    assertNotNull(items);
    assertFalse(items.isEmpty());
    assertTrue(items.size() <= 10);

  }
}