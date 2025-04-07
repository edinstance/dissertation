
resource "aws_cognito_user_pool_client" "frontend_client" {

  name = "${var.environment}-frontend-client"

  # Set the client's user pool to be the one created above
  user_pool_id = aws_cognito_user_pool.user_pool.id

  # Enable and configure OAuth authentication with JWT tokens
  supported_identity_providers = ["COGNITO"]

  # Enable the following flows for client authentication
  explicit_auth_flows = [
    "ALLOW_REFRESH_TOKEN_AUTH",
    "ALLOW_USER_PASSWORD_AUTH",
    "ALLOW_USER_SRP_AUTH"
  ]

  # Keep the refresh token valid for 30 days
  refresh_token_validity = 30

  # Don't generate a client secret
  generate_secret = false
}

resource "aws_cognito_user_pool_client" "backend_client" {

  name = "${var.environment}-backend-client"

  # Set the client's user pool to be the one created above
  user_pool_id = aws_cognito_user_pool.user_pool.id

  # Enable and configure OAuth authentication with JWT tokens
  supported_identity_providers = ["COGNITO"]

  # Enable the following flows for client authentication
  explicit_auth_flows = [
    "ALLOW_REFRESH_TOKEN_AUTH",
    "ALLOW_USER_PASSWORD_AUTH",
  ]

  # Keep the refresh token valid for 30 days
  refresh_token_validity = 30

  # Don't generate a client secret
  generate_secret = false
}
