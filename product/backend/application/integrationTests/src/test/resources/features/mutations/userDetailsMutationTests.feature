Feature: User Details Mutation

  Scenario: Create a user and save their details
    Given a user exists
    When the user saves their details
    Then the server creates the user details in the database
    And the server returns the user with the new details

  Scenario: A user updates their details
    Given a user with details exists
    When the user updates their details
    Then the details are updated in the database
    And the server returns the user with the updated details
