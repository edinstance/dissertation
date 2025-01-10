Feature: Create Item Mutation

  Scenario: Create an item
    Given a user is created using the cognito user
    When the user sends a request to create an item
    Then the server returns the correct item

  Scenario: Updating an item
    Given a user is created using the cognito user
    And an item exists
    When the user updates the item
    Then the server returns the updated item
