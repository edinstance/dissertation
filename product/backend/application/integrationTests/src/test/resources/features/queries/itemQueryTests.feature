Feature: Searching for an item

  Scenario: A user wants to search for an item
    Given a user is created using the cognito user
    And an item the user wants exists
    When a user searches for the item
    Then the server returns the item the user searched for

  Scenario: A user wants to get an item by its id
    Given a user is created using the cognito user
    And an item the user wants exists
    When the user searches for the item by its id
    Then the server returns the item with the correct id

  Scenario: A user searches for an item and gets a paginated result
    Given a user is created using the cognito user
    And there are many items in the application
    When the user requests the items without pagination
    Then the server returns paginated data
    Then the user requests for the second page of data
    And the server returns the second page of items