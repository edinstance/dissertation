Feature: User Query Tests

  Scenario: Create a user and query for them
    Given a user is created using the cognito user
    When the user is queried
    Then the server returns the user details
