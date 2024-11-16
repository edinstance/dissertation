Feature: Api Key Authentication

  Scenario: Check if a request without an api key is denied
    When the client requests the application health without an api key
    Then the server forbids the request

  Scenario: Check if a request with an invalid api key is denied
    When the client requests the application health with an invalid api key
    Then the server does not allow the request

  Scenario: Check if a request with a correct api key works
    When the client requests the application health with a valid api key
    Then the server allows the request and returns the health