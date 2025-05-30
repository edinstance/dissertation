Feature: JWT Authentication for Application Health Endpoint

  Scenario: Client requests health without a JWT token
    When the client requests the application health without a jwt token
    Then the server does not allow the request due to the token

  Scenario: Client requests health with an invalid JWT token
    When the client requests the application health with an invalid jwt token
    Then the server does not allow the request due to the token

  Scenario: Client requests health with a valid JWT token
    When the client requests the application health with a valid jwt token
    Then the server allows the request and returns the health because of the token
