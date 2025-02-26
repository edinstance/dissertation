Feature: Create Bug Report Mutation

  Scenario: Create a bug
    Given a user is created using the cognito user
    When the user reports a bug
    Then the bug is created in jira
    And the bug response is correct
    And the jira ticket is deleted
