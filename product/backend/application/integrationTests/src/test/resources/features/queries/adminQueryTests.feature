Feature: Admin queries

  Scenario: A user tries to access admin data
    Given a user is created using the cognito user
    When a user tries to access admin data
    Then the server denies their request as they are not an admin

  Scenario: An admin requests admin data
    Given a user is created using the cognito user
    When the admin requests the user stats
    Then the server returns the user stats

  Scenario: A user tries to get all the user data
    Given a user is created using the cognito user
    When a user tries to access all the user data
    Then the server denies their request as they are not an admin

  Scenario: An admin tries to get all the user data
    Given a user is created using the cognito user
    When an admin tries to access all the user data
    Then the server returns all the user data