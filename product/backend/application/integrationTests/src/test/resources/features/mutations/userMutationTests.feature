Feature: User Mutations

  Scenario: A new user is created
    When the client sends a request to create a new user
    Then the server creates the user in the database
    And the server returns the new user