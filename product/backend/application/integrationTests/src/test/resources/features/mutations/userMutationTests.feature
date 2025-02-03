Feature: User Mutations

  Scenario: A new user is created
    When the client sends a request to create a new user
    Then the server creates the user in the database
    And the server returns the new user

  Scenario: A user wants to delete their account
    Given there is a user with details who wants to delete their account
    Then the user sends a request to delete their account
    And the server returns a successful delete response

    Scenario: A user that does not exist is deleted
      When a user is deleted but they do not exist
      Then the server returns an unsuccessful delete response