Feature: Health Check

  Scenario: Check the Health of the application
    When the client requests the application health
    Then the server returns the health status